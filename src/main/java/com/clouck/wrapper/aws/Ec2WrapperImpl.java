package com.clouck.wrapper.aws;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.AmazonEC2Client;
import com.amazonaws.services.ec2.model.CreateVolumePermission;
import com.amazonaws.services.ec2.model.DescribeAddressesRequest;
import com.amazonaws.services.ec2.model.DescribeAddressesResult;
import com.amazonaws.services.ec2.model.DescribeDhcpOptionsRequest;
import com.amazonaws.services.ec2.model.DescribeDhcpOptionsResult;
import com.amazonaws.services.ec2.model.DescribeImageAttributeRequest;
import com.amazonaws.services.ec2.model.DescribeImageAttributeResult;
import com.amazonaws.services.ec2.model.DescribeImagesRequest;
import com.amazonaws.services.ec2.model.DescribeImagesResult;
import com.amazonaws.services.ec2.model.DescribeInstanceAttributeRequest;
import com.amazonaws.services.ec2.model.DescribeInstanceAttributeResult;
import com.amazonaws.services.ec2.model.DescribeInstancesRequest;
import com.amazonaws.services.ec2.model.DescribeInstancesResult;
import com.amazonaws.services.ec2.model.DescribeInternetGatewaysRequest;
import com.amazonaws.services.ec2.model.DescribeInternetGatewaysResult;
import com.amazonaws.services.ec2.model.DescribeKeyPairsRequest;
import com.amazonaws.services.ec2.model.DescribeKeyPairsResult;
import com.amazonaws.services.ec2.model.DescribeNetworkAclsRequest;
import com.amazonaws.services.ec2.model.DescribeNetworkAclsResult;
import com.amazonaws.services.ec2.model.DescribeNetworkInterfacesRequest;
import com.amazonaws.services.ec2.model.DescribeNetworkInterfacesResult;
import com.amazonaws.services.ec2.model.DescribePlacementGroupsRequest;
import com.amazonaws.services.ec2.model.DescribePlacementGroupsResult;
import com.amazonaws.services.ec2.model.DescribeRouteTablesRequest;
import com.amazonaws.services.ec2.model.DescribeRouteTablesResult;
import com.amazonaws.services.ec2.model.DescribeSecurityGroupsRequest;
import com.amazonaws.services.ec2.model.DescribeSecurityGroupsResult;
import com.amazonaws.services.ec2.model.DescribeSnapshotAttributeRequest;
import com.amazonaws.services.ec2.model.DescribeSnapshotAttributeResult;
import com.amazonaws.services.ec2.model.DescribeSnapshotsRequest;
import com.amazonaws.services.ec2.model.DescribeSnapshotsResult;
import com.amazonaws.services.ec2.model.DescribeSpotInstanceRequestsRequest;
import com.amazonaws.services.ec2.model.DescribeSpotInstanceRequestsResult;
import com.amazonaws.services.ec2.model.DescribeSubnetsRequest;
import com.amazonaws.services.ec2.model.DescribeSubnetsResult;
import com.amazonaws.services.ec2.model.DescribeVolumeAttributeRequest;
import com.amazonaws.services.ec2.model.DescribeVolumesRequest;
import com.amazonaws.services.ec2.model.DescribeVolumesResult;
import com.amazonaws.services.ec2.model.DescribeVpcsRequest;
import com.amazonaws.services.ec2.model.DescribeVpcsResult;
import com.amazonaws.services.ec2.model.Filter;
import com.amazonaws.services.ec2.model.Image;
import com.amazonaws.services.ec2.model.Instance;
import com.amazonaws.services.ec2.model.LaunchPermission;
import com.amazonaws.services.ec2.model.ProductCode;
import com.amazonaws.services.ec2.model.Reservation;
import com.amazonaws.services.ec2.model.Snapshot;
import com.amazonaws.services.ec2.model.Volume;
import com.clouck.application.Ec2Filter;
import com.clouck.converter.Ec2Converter;
import com.clouck.model.Account;
import com.clouck.model.Region;
import com.clouck.model.aws.AbstractResource;
import com.clouck.model.aws.ec2.Ec2InstanceAttribute;

@Component
public class Ec2WrapperImpl implements Ec2Wrapper {
    private static final Logger log = LoggerFactory.getLogger(Ec2WrapperImpl.class);

    @Autowired
    private Ec2Converter converter;

    private AmazonEC2 findClient(Account account, Region region) {
        // TODO: need to config client config parameter. ignore it for now.
        // TODO: need a cached version based on account and region as key
        AWSCredentials credential = new BasicAWSCredentials(account.getAccessKeyId(), account.getSecretAccessKey());
        AmazonEC2 ec2 = new AmazonEC2Client(credential);
        ec2.setRegion(com.amazonaws.regions.Region.getRegion(region.getRegions()));
        return ec2;
    }

    @Override
    public List<AbstractResource<?>> describeSpotInstanceRequests(Account account, Region region, DateTime dt, Ec2Filter... filters) {
        AmazonEC2 ec2 = findClient(account, region);

        DescribeSpotInstanceRequestsRequest req = new DescribeSpotInstanceRequestsRequest();
        for (Ec2Filter filter : filters) {
            Filter f = new Filter().withName(filter.getName()).withValues(filter.getValues());
            req.withFilters(f);
        }

        log.debug("start describing spot instance requests for account:{} in region:{} via api", account.getId() + "=>" + account.getName(), region);
        DescribeSpotInstanceRequestsResult res = ec2.describeSpotInstanceRequests(req);

        return converter.toEc2SpotInstanceRequest(res.getSpotInstanceRequests(), account.getId(), region, dt);
    }

    @Override
    public List<AbstractResource<?>> describeReservations(Account account, Region region, DateTime dt, Ec2Filter... filters) {
        AmazonEC2 ec2 = findClient(account, region);

        DescribeInstancesRequest req = new DescribeInstancesRequest();
        for (Ec2Filter filter : filters) {
            Filter f = new Filter().withName(filter.getName()).withValues(filter.getValues());
            req.withFilters(f);
        }

        log.debug("start describing instances for account:{} in region:{} via api", account.getId() + "=>" + account.getName(), region);
        DescribeInstancesResult res = ec2.describeInstances(req);
        List<Map<String, Ec2InstanceAttribute>> attributes = new ArrayList<>();
        for (Reservation r : res.getReservations()) {
            Map<String, Ec2InstanceAttribute> attribute = new HashMap<>();
            for (Instance i : r.getInstances()) {
                Boolean terminationProtection = findTerminationProtection(account, region, i.getInstanceId());
                String shutdownBehavior = findShutdownBehavior(account, region, i.getInstanceId());
                String userData = findUserData(account, region, i.getInstanceId());
                Ec2InstanceAttribute ia = new Ec2InstanceAttribute(terminationProtection, shutdownBehavior, userData);
                attribute.put(i.getInstanceId(), ia);
            }
            attributes.add(attribute);
        }
        return converter.toEc2Reservations(res.getReservations(), attributes, account.getId(), region, dt);
    }

    @Override
    public Boolean findTerminationProtection(Account account, Region region, String instanceId) {
        AmazonEC2 ec2 = findClient(account, region);

        DescribeInstanceAttributeRequest req = new DescribeInstanceAttributeRequest();
        req.setAttribute("disableApiTermination");
        req.setInstanceId(instanceId);

        log.debug("start describing instance termination protection for account:{} in region:{} via api", account.getId() + "=>" + account.getName(), region);
        DescribeInstanceAttributeResult res = ec2.describeInstanceAttribute(req);

        return res.getInstanceAttribute().getDisableApiTermination();
    }

    @Override
    public String findShutdownBehavior(Account account, Region region, String instanceId) {
        AmazonEC2 ec2 = findClient(account, region);

        DescribeInstanceAttributeRequest req = new DescribeInstanceAttributeRequest();
        req.setAttribute("instanceInitiatedShutdownBehavior");
        req.setInstanceId(instanceId);

        log.debug("start describing instance shutdown behavior for account:{} in region:{} via api", account.getId() + "=>" + account.getName(), region);
        DescribeInstanceAttributeResult res = ec2.describeInstanceAttribute(req);

        return res.getInstanceAttribute().getInstanceInitiatedShutdownBehavior();
    }

    @Override
    public String findUserData(Account account, Region region, String instanceId) {
        AmazonEC2 ec2 = findClient(account, region);

        DescribeInstanceAttributeRequest req = new DescribeInstanceAttributeRequest();
        req.setAttribute("userData");
        req.setInstanceId(instanceId);

        log.debug("start describing instance user data for account:{} in region:{} via api", account.getId() + "=>" + account.getName(), region);
        DescribeInstanceAttributeResult res = ec2.describeInstanceAttribute(req);

        return res.getInstanceAttribute().getUserData();
    }

    @Override
    public List<AbstractResource<?>> describeSecurityGroups(Account account, Region region, DateTime dt, Ec2Filter... filters) {
        AmazonEC2 ec2 = findClient(account, region);

        DescribeSecurityGroupsRequest req = new DescribeSecurityGroupsRequest();
        for (Ec2Filter filter : filters) {
            Filter f = new Filter().withName(filter.getName()).withValues(filter.getValues());
            req.withFilters(f);
        }
        log.debug("start describing security groups for account:{} in region:{} via api", account.getId() + "=>" + account.getName(), region);
        DescribeSecurityGroupsResult res = ec2.describeSecurityGroups(req);

        return converter.toEc2SecurityGroups(res.getSecurityGroups(), account.getId(), region, dt);
    }

    @Override
    public List<AbstractResource<?>> describeAMIs(Account account, Region region, DateTime dt, Ec2Filter... filters) {
        AmazonEC2 ec2 = findClient(account, region);

        DescribeImagesRequest req = new DescribeImagesRequest();
        for (Ec2Filter filter : filters) {
            Filter f = new Filter().withName(filter.getName()).withValues(filter.getValues());
            req.withFilters(f);
        }

        log.debug("start describing amis for account:{} in region:{} via api", account.getId() + "=>" + account.getName(), region);
        DescribeImagesResult res = ec2.describeImages(req);
        
        List<List<LaunchPermission>> imageLaunchPermissions = new ArrayList<>();
        for (Image image : res.getImages()) {
            imageLaunchPermissions.add(findImageLaunchPermissions(account, region, image.getImageId()));
        }

        return converter.toEc2AMIs(res.getImages(), imageLaunchPermissions, account.getId(), region, dt);
    }

    @Override
    public List<LaunchPermission> findImageLaunchPermissions(Account account, Region region, String imageId) {
        AmazonEC2 ec2 = findClient(account, region);

        DescribeImageAttributeRequest req = new DescribeImageAttributeRequest();
        req.setAttribute("launchPermission");
        req.setImageId(imageId);

        log.debug("start describing image launch permission for account:{} in region:{} via api", account.getId() + "=>" + account.getName(), region);
        DescribeImageAttributeResult res = ec2.describeImageAttribute(req);

        List<LaunchPermission> launchPermissions = res.getImageAttribute().getLaunchPermissions();
        return removeGroup(launchPermissions);
    }

    private List<LaunchPermission> removeGroup(List<LaunchPermission> permissions) {
        List<LaunchPermission> result = new ArrayList<>();
        for (LaunchPermission lp : permissions) {
            if (lp.getGroup() == null) {
                result.add(lp);
            }
        }
        return result;
    }

    @Override
    public List<AbstractResource<?>> describeVolumes(Account account, Region region, DateTime dt, Ec2Filter... filters) {
        AmazonEC2 ec2 = findClient(account, region);

        DescribeVolumesRequest req = new DescribeVolumesRequest();
        for (Ec2Filter filter : filters) {
            Filter f = new Filter().withName(filter.getName()).withValues(filter.getValues());
            req.withFilters(f);
        }
        log.debug("start describing volumes for account:{} in region:{} via api", account.getId() + "=>" + account.getName(), region);
        DescribeVolumesResult res = ec2.describeVolumes(req);

        List<Boolean> autoEnableIOs = new ArrayList<>();
        List<List<ProductCode>> productCodes = new ArrayList<>();
        for (Volume volume : res.getVolumes()) {
            autoEnableIOs.add(findAutoEnableIO(account, region, volume.getVolumeId()));
            productCodes.add(findProductCodes(account, region, volume.getVolumeId()));
        }

        return converter.toEc2Volumes(res.getVolumes(), autoEnableIOs, productCodes, account.getId(), region, dt);
    }

    @Override
    public Boolean findAutoEnableIO(Account account, Region region, String volumeId) {
        AmazonEC2 ec2 = findClient(account, region);

        DescribeVolumeAttributeRequest req = new DescribeVolumeAttributeRequest();
        req.setVolumeId(volumeId);
        req.setAttribute("autoEnableIO");

        log.debug("start describing volume attribute auto enable io for account:{} in region:{} via api", account.getId() + "=>" + account.getName(), region);
        return ec2.describeVolumeAttribute(req).getAutoEnableIO();
    }

    @Override
    public List<ProductCode> findProductCodes(Account account, Region region, String volumeId) {
        AmazonEC2 ec2 = findClient(account, region);

        DescribeVolumeAttributeRequest req = new DescribeVolumeAttributeRequest();
        req.setVolumeId(volumeId);
        req.setAttribute("productCodes");

        log.debug("start describing volume attribute product codes for account:{} in region:{} via api", account.getId() + "=>" + account.getName(), region);
        return ec2.describeVolumeAttribute(req).getProductCodes();
    }

//    public List<LaunchPermission> findxxxs(Account account, Region region, String volumeId) {
//        AmazonEC2 ec2 = findClient(account, region);
//
//        DescribeVolumeStatusRequest req = new DescribeVolumeStatusRequest();
//        req.setVolumeIds(Lists.newArrayList(volumeId));
//
//        log.debug("start describing image launch permission for account:{} in region:{} via api", account.getId() + "=>" + account.getName(), region);
//        DescribeVolumeStatusResult res = ec2.describeVolumeStatus(req);
//
//        List<VolumeStatusItem> volumeStatuses = res.getVolumeStatuses();
//        return removeGroup(launchPermissions);
//    }

    @Override
    public List<AbstractResource<?>> describeSnapshots(Account account, Region region, DateTime dt, Ec2Filter... filters) {
        AmazonEC2 ec2 = findClient(account, region);

        DescribeSnapshotsRequest req = new DescribeSnapshotsRequest();
        for (Ec2Filter filter : filters) {
            Filter f = new Filter().withName(filter.getName()).withValues(filter.getValues());
            req.withFilters(f);
        }

        log.debug("start describing snapshots for account:{} in region:{} via api", account.getId() + "=>" + account.getName(), region);
        DescribeSnapshotsResult res = ec2.describeSnapshots(req);
        
        List<List<CreateVolumePermission>> createVolumePermissions = new ArrayList<>();
        List<List<ProductCode>> productCodes = new ArrayList<>();
        for (Snapshot snapShot : res.getSnapshots()) {
            productCodes.add(findSnapshotProductCodes(account, region, snapShot.getSnapshotId()));
            createVolumePermissions.add(findCreateVolumePermissions(account, region, snapShot.getSnapshotId()));
        }

        return converter.toEc2Snapshots(res.getSnapshots(), createVolumePermissions, productCodes, account.getId(), region, dt);
    }

    @Override
    public List<CreateVolumePermission> findCreateVolumePermissions(Account account, Region region, String snapshotId) {
        AmazonEC2 ec2 = findClient(account, region);

        DescribeSnapshotAttributeRequest req = new DescribeSnapshotAttributeRequest();
        req.setAttribute("createVolumePermission");
        req.setSnapshotId(snapshotId);

        log.debug("start describing snapshot create volume permission for account:{} in region:{} via api", account.getId() + "=>" + account.getName(), region);
        DescribeSnapshotAttributeResult res = ec2.describeSnapshotAttribute(req);

        return res.getCreateVolumePermissions();
    }

    @Override
    public List<ProductCode> findSnapshotProductCodes(Account account, Region region, String snapshotId) {
        AmazonEC2 ec2 = findClient(account, region);

        DescribeSnapshotAttributeRequest req = new DescribeSnapshotAttributeRequest();
        req.setAttribute("productCodes");
        req.setSnapshotId(snapshotId);

        log.debug("start describing snapshot productCodes for account:{} in region:{} via api", account.getId() + "=>" + account.getName(), region);
        DescribeSnapshotAttributeResult res = ec2.describeSnapshotAttribute(req);

        return res.getProductCodes();
    }

    @Override
    public List<AbstractResource<?>> describeKeyPairs(Account account, Region region, DateTime dt, Ec2Filter... filters) {
        AmazonEC2 ec2 = findClient(account, region);

        DescribeKeyPairsRequest req = new DescribeKeyPairsRequest();
        for (Ec2Filter filter : filters) {
            Filter f = new Filter().withName(filter.getName()).withValues(filter.getValues());
            req.withFilters(f);
        }

        log.debug("start describing key pairs for account:{} in region:{} via api", account.getId() + "=>" + account.getName(), region);
        DescribeKeyPairsResult res = ec2.describeKeyPairs(req);

        return converter.toEc2KeyPairs(res.getKeyPairs(), account.getId(), region, dt);
    }
    
    @Override
    public List<AbstractResource<?>> describeElasticIPs(Account account, Region region, DateTime dt, Ec2Filter... filters) {
        AmazonEC2 ec2 = findClient(account, region);

        DescribeAddressesRequest req = new DescribeAddressesRequest();
        for (Ec2Filter filter : filters) {
            Filter f = new Filter().withName(filter.getName()).withValues(filter.getValues());
            req.withFilters(f);
        }

        log.debug("start describing elastic ips for account:{} in region:{} via api", account.getId() + "=>" + account.getName(), region);
        DescribeAddressesResult res = ec2.describeAddresses(req);

        return converter.toEc2ElasticIPs(res.getAddresses(), account.getId(), region, dt);
    }
    
    @Override
    public List<AbstractResource<?>> describePlacementGroups(Account account, Region region, DateTime dt, Ec2Filter... filters) {
        AmazonEC2 ec2 = findClient(account, region);

        DescribePlacementGroupsRequest req = new DescribePlacementGroupsRequest();
        for (Ec2Filter filter : filters) {
            Filter f = new Filter().withName(filter.getName()).withValues(filter.getValues());
            req.withFilters(f);
        }

        log.debug("start describing placement groups for account:{} in region:{} via api", account.getId() + "=>" + account.getName(), region);
        DescribePlacementGroupsResult res = ec2.describePlacementGroups(req);

        return converter.toEc2PlacementGroups(res.getPlacementGroups(), account.getId(), region, dt);
    }

    @Override
    public List<AbstractResource<?>> describeNetworkInterfaces(Account account, Region region, DateTime dt, Ec2Filter... filters) {
        AmazonEC2 ec2 = findClient(account, region);

        DescribeNetworkInterfacesRequest req = new DescribeNetworkInterfacesRequest();
        for (Ec2Filter filter : filters) {
            Filter f = new Filter().withName(filter.getName()).withValues(filter.getValues());
            req.withFilters(f);
        }

        log.debug("start describing network interfaces for account:{} in region:{} via api", account.getId() + "=>" + account.getName(), region);
        DescribeNetworkInterfacesResult res = ec2.describeNetworkInterfaces(req);

        return converter.toEc2NetworkInterfaces(res.getNetworkInterfaces(), account.getId(), region, dt);
    }

    @Override
    public List<AbstractResource<?>> describeVpcs(Account account, Region region, DateTime dt, Ec2Filter... filters) {
        AmazonEC2 ec2 = findClient(account, region);

        DescribeVpcsRequest req = new DescribeVpcsRequest();
        for (Ec2Filter filter : filters) {
            Filter f = new Filter().withName(filter.getName()).withValues(filter.getValues());
            req.withFilters(f);
        }

        log.debug("start describing vpcs for account:{} in region:{} via api", account.getId() + "=>" + account.getName(), region);
        DescribeVpcsResult res = ec2.describeVpcs(req);

        return converter.toVpcVpcs(res.getVpcs(), account.getId(), region, dt);
    }

    @Override
    public List<AbstractResource<?>> describeSubnets(Account account, Region region, DateTime dt, Ec2Filter... filters) {
        AmazonEC2 ec2 = findClient(account, region);

        DescribeSubnetsRequest req = new DescribeSubnetsRequest();
        for (Ec2Filter filter : filters) {
            Filter f = new Filter().withName(filter.getName()).withValues(filter.getValues());
            req.withFilters(f);
        }

        log.debug("start describing subnets for account:{} in region:{} via api", account.getId() + "=>" + account.getName(), region);
        DescribeSubnetsResult res = ec2.describeSubnets(req);

        return converter.toVpcSubnets(res.getSubnets(), account.getId(), region, dt);
    }
    
    @Override
    public List<AbstractResource<?>> describeRouteTables(Account account, Region region, DateTime dt, Ec2Filter... filters) {
        AmazonEC2 ec2 = findClient(account, region);

        DescribeRouteTablesRequest req = new DescribeRouteTablesRequest();
        for (Ec2Filter filter : filters) {
            Filter f = new Filter().withName(filter.getName()).withValues(filter.getValues());
            req.withFilters(f);
        }

        log.debug("start describing route tables for account:{} in region:{} via api", account.getId() + "=>" + account.getName(), region);
        DescribeRouteTablesResult res = ec2.describeRouteTables(req);

        return converter.toVpcRouteTables(res.getRouteTables(), account.getId(), region, dt);
    }
    
    @Override
    public List<AbstractResource<?>> describeInternetGateways(Account account, Region region, DateTime dt, Ec2Filter... filters) {
        AmazonEC2 ec2 = findClient(account, region);

        DescribeInternetGatewaysRequest req = new DescribeInternetGatewaysRequest();
        for (Ec2Filter filter : filters) {
            Filter f = new Filter().withName(filter.getName()).withValues(filter.getValues());
            req.withFilters(f);
        }

        log.debug("start describing internet gateways for account:{} in region:{} via api", account.getId() + "=>" + account.getName(), region);
        DescribeInternetGatewaysResult res = ec2.describeInternetGateways(req);

        return converter.toVpcInternetGateways(res.getInternetGateways(), account.getId(), region, dt);
    }
    
    @Override
    public List<AbstractResource<?>> describeDhcpOptions(Account account, Region region, DateTime dt, Ec2Filter... filters) {
        AmazonEC2 ec2 = findClient(account, region);

        DescribeDhcpOptionsRequest req = new DescribeDhcpOptionsRequest();
        for (Ec2Filter filter : filters) {
            Filter f = new Filter().withName(filter.getName()).withValues(filter.getValues());
            req.withFilters(f);
        }

        log.debug("start describing dhcp options for account:{} in region:{} via api", account.getId() + "=>" + account.getName(), region);
        DescribeDhcpOptionsResult res = ec2.describeDhcpOptions(req);

        return converter.toVpcDhcpOptions(res.getDhcpOptions(), account.getId(), region, dt);
    }
    
    @Override
    public List<AbstractResource<?>> describeNetworkAcls(Account account, Region region, DateTime dt, Ec2Filter... filters) {
        AmazonEC2 ec2 = findClient(account, region);

        DescribeNetworkAclsRequest req = new DescribeNetworkAclsRequest();
        for (Ec2Filter filter : filters) {
            Filter f = new Filter().withName(filter.getName()).withValues(filter.getValues());
            req.withFilters(f);
        }

        log.debug("start describing network acls for account:{} in region:{} via api", account.getId() + "=>" + account.getName(), region);
        DescribeNetworkAclsResult res = ec2.describeNetworkAcls(req);

        return converter.toVpcNetworkAcls(res.getNetworkAcls(), account.getId(), region, dt);
    }
}
