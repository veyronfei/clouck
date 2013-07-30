package com.clouck.model.aws.ec2;

import static com.clouck.util.ResourceUtil.notEqual;
import static com.clouck.util.ResourceUtil.notEqualCollection;

import java.util.Collections;
import java.util.List;

import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

import com.amazonaws.services.ec2.model.InstanceNetworkInterfaceSpecification;
import com.amazonaws.services.ec2.model.LaunchSpecification;
import com.amazonaws.services.ec2.model.SpotInstanceRequest;
import com.clouck.model.aws.AbstractResource;
import com.clouck.model.aws.comparator.PrivateIpAddressSpecificationComparator;

@Document(collection = "ec2_spot_instance_request")
@TypeAlias(value = "ec2_spot_instance_request")
@SuppressWarnings("serial")
public class Ec2SpotInstanceRequest extends AbstractResource<SpotInstanceRequest> {

    @Override
    @SuppressWarnings("rawtypes")
    protected boolean isEqual(AbstractResource newResource) {
        SpotInstanceRequest oldSIR = this.getResource();
        SpotInstanceRequest newSIR = (SpotInstanceRequest) newResource.getResource();

        if (notEqual(oldSIR.getSpotInstanceRequestId(), newSIR.getSpotInstanceRequestId())) return false;
        if (notEqual(oldSIR.getSpotPrice(), newSIR.getSpotPrice())) return false;
        if (notEqual(oldSIR.getType(), newSIR.getType())) return false;
        if (notEqual(oldSIR.getState(), newSIR.getState())) return false;
        if (notEqual(oldSIR.getFault(), newSIR.getFault())) return false;
        if (notEqual(oldSIR.getStatus().getCode(), newSIR.getStatus().getCode())) return false;
        //ignore update time and message, as they are updated quite often
//        if (notEqual(oldSIR.getStatus().getUpdateTime(), newSIR.getStatus().getUpdateTime())) return false;
//        if (notEqual(oldSIR.getStatus().getMessage(), newSIR.getStatus().getMessage())) return false;
        if (notEqual(oldSIR.getValidFrom(), newSIR.getValidFrom())) return false;
        if (notEqual(oldSIR.getValidUntil(), newSIR.getValidUntil())) return false;
        if (notEqual(oldSIR.getLaunchGroup(), newSIR.getLaunchGroup())) return false;
        if (notEqual(oldSIR.getAvailabilityZoneGroup(), newSIR.getAvailabilityZoneGroup())) return false;
        if (notEqualLaunchSpecification(oldSIR.getLaunchSpecification(), newSIR.getLaunchSpecification())) return false;
        if (notEqual(oldSIR.getInstanceId(), newSIR.getInstanceId())) return false;
        if (notEqual(oldSIR.getCreateTime(), newSIR.getCreateTime())) return false;
        if (notEqual(oldSIR.getProductDescription(), newSIR.getProductDescription())) return false;
        if (notEqualCollection(oldSIR.getTags(), newSIR.getTags())) return false;
        if (notEqual(oldSIR.getLaunchedAvailabilityZone(), newSIR.getLaunchedAvailabilityZone())) return false;

        return true;
    }
    
    public boolean notEqualLaunchSpecification(LaunchSpecification oldLS, LaunchSpecification newLS) {
        if (notEqual(oldLS.getImageId(), newLS.getImageId())) return true;
        if (notEqual(oldLS.getKeyName(), newLS.getKeyName())) return true;
        if (notEqualCollection(oldLS.getAllSecurityGroups(), newLS.getAllSecurityGroups())) return true;
        if (notEqualCollection(oldLS.getSecurityGroups(), newLS.getSecurityGroups())) return true;
        if (notEqual(oldLS.getUserData(), newLS.getUserData())) return true;
        if (notEqual(oldLS.getAddressingType(), newLS.getAddressingType())) return true;
        if (notEqual(oldLS.getInstanceType(), newLS.getInstanceType())) return true;
        if (notEqual(oldLS.getPlacement(), newLS.getPlacement())) return true;
        if (notEqual(oldLS.getKernelId(), newLS.getKernelId())) return true;
        if (notEqual(oldLS.getRamdiskId(), newLS.getRamdiskId())) return true;
        if (notEqualCollection(oldLS.getBlockDeviceMappings(), newLS.getBlockDeviceMappings())) return true;
        if (notEqual(oldLS.getMonitoringEnabled(), newLS.getMonitoringEnabled())) return true;
        if (notEqual(oldLS.getSubnetId(), newLS.getSubnetId())) return true;
        if (notEqualInstanceNetworkInterfaceSpecifications(oldLS.getNetworkInterfaces(), newLS.getNetworkInterfaces())) return true;
        if (notEqual(oldLS.getIamInstanceProfile(), newLS.getIamInstanceProfile())) return true;
        if (notEqual(oldLS.getEbsOptimized(), newLS.getEbsOptimized())) return true;

        return false;
    }

    public boolean notEqualInstanceNetworkInterfaceSpecifications(List<InstanceNetworkInterfaceSpecification> l1, List<InstanceNetworkInterfaceSpecification> l2) {
        for (InstanceNetworkInterfaceSpecification inis : l1) {
            sortInstanceNetworkInterfaceSpecification(inis);
        }
        for (InstanceNetworkInterfaceSpecification inis : l2) {
            sortInstanceNetworkInterfaceSpecification(inis);
        }
        return notEqualCollection(l1, l2);
    }

    public void sortInstanceNetworkInterfaceSpecification(InstanceNetworkInterfaceSpecification inis) {
        Collections.sort(inis.getGroups());
        Collections.sort(inis.getPrivateIpAddresses(), new PrivateIpAddressSpecificationComparator());
    }
}
