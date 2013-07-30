package com.clouck.service;

import java.util.Collection;
import java.util.List;

import org.apache.commons.lang.Validate;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.clouck.application.Ec2Filter;
import com.clouck.application.Ec2FilterName;
import com.clouck.converter.Ec2Converter;
import com.clouck.exception.ClouckUnexpectedConditionException;
import com.clouck.exception.CloudVersionIllegalStateException;
import com.clouck.model.Account;
import com.clouck.model.Region;
import com.clouck.model.ResourceType;
import com.clouck.model.aws.AbstractResource;
import com.clouck.model.aws.ec2.Ec2InstanceState;
import com.clouck.model.aws.ec2.Ec2SpotInstanceRequestState;
import com.clouck.model.aws.ec2.Ec2Version;
import com.clouck.util.ResourceUtil;
import com.clouck.validator.Ec2ResourceValidator;
import com.clouck.wrapper.aws.AsWrapper;
import com.clouck.wrapper.aws.Ec2Wrapper;
import com.clouck.wrapper.aws.ElbWrapper;
import com.clouck.wrapper.aws.IamWrapper;
import com.clouck.wrapper.rabbit.MQWrapper;
import com.google.common.base.Optional;

@Service
public class ResourceServiceImpl implements ResourceService {
    private static final Logger log = LoggerFactory.getLogger(ResourceServiceImpl.class);

    @Autowired
    private Ec2Converter converter;
    @Autowired
    private AwsService awsService;
    @Autowired
    private BaseService baseService;
    @Autowired
    private Ec2ResourceValidator validator;
    @Autowired
    private Ec2Wrapper ec2;
    @Autowired
    private ElbWrapper elb;
    @Autowired
    private AsWrapper as;
    @Autowired
    private IamWrapper iam;
    @Autowired
    private MQWrapper mQWrapper;
    @Autowired
    private ResourceUtil resourceUtil;

    @Override
    public void scanAccount(Account account) {
        log.debug("============scan account:({}==>{})============", account.getId(), account.getName());
        for (ResourceType resourceType : ResourceType.findScaningResourceTypes()) {
            if (resourceType.isMultiRegion()) {
                for (Region region : Region.findAvailableRegions(resourceType)) {
                    mQWrapper.sendScanResourceMessage(account.getId(), resourceType, region);
                }
            } else {
                mQWrapper.sendScanResourceMessage(account.getId(), resourceType);
            }
        }
    }

    @Override
    public void convertEc2Reservation2Ec2Instance(Account account) {
        Validate.notNull(account);
        //TODO: Maybe have a locker here as well. just like others
        String accountId = account.getId();

        for (Region region : Region.findAvailableRegions(ResourceType.Ec2_Reservation)) {
            Optional<Ec2Version> oLatestEc2Version = awsService.findLatestEc2Version(accountId, ResourceType.Ec2_Instance, region);
            List<Ec2Version> ec2ReservationVersions = null;
            if (oLatestEc2Version.isPresent()) {
                DateTime dt = new DateTime(oLatestEc2Version.get().getTimeDetected());
                ec2ReservationVersions = awsService.findEc2VersionsFromExcludeOrderByTimeDetected(accountId, ResourceType.Ec2_Reservation, region, dt);
            } else {
                ec2ReservationVersions = awsService.findEc2VersionsOrderByTimeDetectedAsc(accountId, ResourceType.Ec2_Reservation, region);
            }
            for (Ec2Version ec2ReservationVersion : ec2ReservationVersions) {
                List<AbstractResource<?>> ec2Reservations = awsService.findResources(ec2ReservationVersion.getResourceIds(), ResourceType.Ec2_Reservation);
                List<AbstractResource<?>> ec2Instances = converter.toEc2InstancesFromEc2Reservations(ec2Reservations, ec2ReservationVersion.getAccountId(), region, new DateTime(ec2ReservationVersion.getTimeDetected()));
                boolean isNewEc2VersionAdded = addNewResources(account, region, ResourceType.Ec2_Instance, ec2Instances, new DateTime(ec2ReservationVersion.getTimeDetected()));
                if (!isNewEc2VersionAdded) {
                    throw new ClouckUnexpectedConditionException(String.format("ec2 reservation version:%s " +
                            "should associate with one ec2 instance version, but got none added.", ec2ReservationVersion.getId()));
                }
            }
        }
    }

    /**
     * 
     * @param account
     * @param region
     * @param resourceType
     * @param newResources
     * @param dt the time we scan the resources
     */
    @Override
    public boolean addNewResources(Account account, Region region, ResourceType resourceType, List<AbstractResource<?>> newResources, DateTime dt) {
        validator.isSameTime(newResources, dt);
        validator.isLatestTime(account, region, resourceType, dt);

        boolean isNewEc2VersionAdded = false;
        // find existing resources
        Optional<Ec2Version> oLatestVersion = awsService.findLatestEc2Version(account.getId(), resourceType, region);
        if (!oLatestVersion.isPresent()) {
            // maybe first time scan or a new region added
            Ec2Version newEc2Version = new Ec2Version(account.getId(), region, dt.toDate(), resourceType);
            saveResourcesAndEc2Version(newResources, newEc2Version);
            isNewEc2VersionAdded = true;
        } else {
            List<AbstractResource<?>> existingResources = awsService.findResources(oLatestVersion.get().getResourceIds(), resourceType);
            // compare resources
            CompareResourceResult<AbstractResource<?>> result = resourceUtil.compareResources(existingResources, newResources);

            // save them into db
            if (result.getAddedResources().size() != 0 || result.getDeletedResourceIds().size() != 0) {
                Ec2Version newEc2Version = new Ec2Version(account.getId(), region, dt.toDate(), resourceType);
                for (String id : result.getUnchangedResourceIds()) {
                    newEc2Version.getResourceIds().add(id);
                }
                saveResourcesAndEc2Version(result.getAddedResources(), newEc2Version);
                isNewEc2VersionAdded = true;
            }
        }
        return isNewEc2VersionAdded;
    }

    private void saveResourcesAndEc2Version(Collection<? extends AbstractResource<?>> newResources, Ec2Version newEc2Version) {
        baseService.insertAll(newResources);
        for (AbstractResource<?> newResource : newResources) {
            newEc2Version.getResourceIds().add(newResource.getId());
        }
        baseService.save(newEc2Version);
    }


    @Override
    public List<AbstractResource<?>> findNewResources(Account account, Region region, ResourceType resourceType, DateTime dt) {
        List<AbstractResource<?>> newResources = null;
        switch (resourceType) {
        case Ec2_Reservation:
            Ec2Filter instanceFilter = new Ec2Filter().
                withName(Ec2FilterName.INSTANCE_STATE_NAME).withValue(Ec2InstanceState.Pending.getState()).
                withValue(Ec2InstanceState.Running.getState()).withValue(Ec2InstanceState.Shutting_Down.getState()).
                withValue(Ec2InstanceState.Stopping.getState()).withValue(Ec2InstanceState.Stopped.getState());
            newResources = ec2.describeReservations(account, region, dt, instanceFilter);
            break;
        case Ec2_Security_Group:
            newResources = ec2.describeSecurityGroups(account, region, dt);
            break;
        case Ec2_Load_Balancer:
            newResources = elb.describeLoadBalancers(account, region, dt);
            break;
        case Ec2_Ami:
            Ec2Filter imageFilter = new Ec2Filter().withName(Ec2FilterName.OWNER_ID).withValue(account.getAccountNumber());
            newResources = ec2.describeAMIs(account, region, dt, imageFilter);
            break;
        case Ec2_Volume:
            newResources = ec2.describeVolumes(account, region, dt);
            break;
        case Ec2_Snapshot:
            Ec2Filter snapshotFilter = new Ec2Filter().withName(Ec2FilterName.OWNER_ID).withValue(account.getAccountNumber());
            newResources = ec2.describeSnapshots(account, region, dt, snapshotFilter);
            break;
        case Ec2_Key_Pair:
            newResources = ec2.describeKeyPairs(account, region, dt);
            break;
        case Ec2_Launch_Configuration:
            newResources = as.describeLaunchConfigurations(account, region, dt);
            break;
        case Ec2_Auto_Scaling_Group:
            newResources = as.describeAutoScalingGroups(account, region, dt);
            break;
        case Ec2_Elastic_IP:
            newResources = ec2.describeElasticIPs(account, region, dt);
            break;
        case Ec2_Placement_Group:
            newResources = ec2.describePlacementGroups(account, region, dt);
            break;
        case Ec2_Network_Interface:
            newResources = ec2.describeNetworkInterfaces(account, region, dt);
            break;
        case Ec2_Spot_Instance_Request:
            Ec2Filter spotInstanceRequestFilter = new Ec2Filter().
                withName(Ec2FilterName.STATE).withValue(Ec2SpotInstanceRequestState.Open.getState()).
                withValue(Ec2SpotInstanceRequestState.Active.getState()).withValue(Ec2SpotInstanceRequestState.Closed.getState()).
                withValue(Ec2SpotInstanceRequestState.Failed.getState());
            newResources = ec2.describeSpotInstanceRequests(account, region, dt, spotInstanceRequestFilter);
            break;
        case Iam_Group:
            newResources = iam.listGroups(account, dt);
            break;
        case Iam_User:
            newResources = iam.listUsers(account, dt);
            break;
        case Iam_Role:
            newResources = iam.listRoles(account, dt);
            break;
        case Vpc_Vpc:
            newResources = ec2.describeVpcs(account, region, dt);
            break;
        case Vpc_Subnet:
            newResources = ec2.describeSubnets(account, region, dt);
            break;
        case Vpc_Route_Table:
            newResources = ec2.describeRouteTables(account, region, dt);
            break;
        case Vpc_Internet_Gateway:
            newResources = ec2.describeInternetGateways(account, region, dt);
            break;
        case Vpc_DhcpOptions:
            newResources = ec2.describeDhcpOptions(account, region, dt);
            break;
        case Vpc_NetworkAcl:
            newResources = ec2.describeNetworkAcls(account, region, dt);
            break;
        default:
            throw new CloudVersionIllegalStateException("invalid resource type:" + resourceType);
        }
        return newResources;
    }
}
