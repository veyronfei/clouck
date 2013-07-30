package com.clouck.model.aws.ec2;

import static com.clouck.util.ResourceUtil.notEqual;
import static com.clouck.util.ResourceUtil.notEqualCollection;

import java.util.Collections;
import java.util.List;

import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

import com.amazonaws.services.ec2.model.Instance;
import com.amazonaws.services.ec2.model.InstanceNetworkInterface;
import com.clouck.model.aws.AbstractResource;
import com.clouck.model.aws.comparator.GroupIdentifierComparator;
import com.clouck.model.aws.comparator.InstancePrivateIpAddressComparator;

@Document(collection = "ec2_instance")
@TypeAlias(value = "ec2_instance")
@SuppressWarnings("serial")
public class Ec2Instance extends AbstractResource<Instance> {
    private Boolean terminationProtection;
    private String shutdownBehavior;
    private String userData;
    //this exists as a convenient value, so its not in the isEqual.
    //this value is obtained from ec2Reservation
    private String reservationId;

    @Override
    @SuppressWarnings("rawtypes")
    protected boolean isEqual(AbstractResource newResource) {
        Instance oldInstance = this.getResource();
        Ec2Instance newEc2Instance = (Ec2Instance) newResource;
        Instance newInstance = newEc2Instance.getResource();

        if (notEqual(oldInstance.getInstanceId(), newInstance.getInstanceId())) return false;
        if (notEqual(oldInstance.getImageId(), newInstance.getImageId())) return false;
        if (notEqual(oldInstance.getState(), newInstance.getState())) return false;
        if (notEqual(oldInstance.getPrivateDnsName(), newInstance.getPrivateDnsName())) return false;
        if (notEqual(oldInstance.getPublicDnsName(), newInstance.getPublicDnsName())) return false;
        if (notEqual(oldInstance.getStateTransitionReason(), newInstance.getStateTransitionReason())) return false;
        if (notEqual(oldInstance.getKeyName(), newInstance.getKeyName())) return false;
        if (notEqual(oldInstance.getAmiLaunchIndex(), newInstance.getAmiLaunchIndex())) return false;
        if (notEqualCollection(oldInstance.getProductCodes(), newInstance.getProductCodes())) return false;
        if (notEqual(oldInstance.getInstanceType(), newInstance.getInstanceType())) return false;
        if (notEqual(oldInstance.getLaunchTime(), newInstance.getLaunchTime())) return false;
        if (notEqual(oldInstance.getPlacement(), newInstance.getPlacement())) return false;
        if (notEqual(oldInstance.getKernelId(), newInstance.getKernelId())) return false;
        if (notEqual(oldInstance.getRamdiskId(), newInstance.getRamdiskId())) return false;
        if (notEqual(oldInstance.getPlatform(), newInstance.getPlatform())) return false;
        if (notEqual(oldInstance.getMonitoring(), newInstance.getMonitoring())) return false;
        if (notEqual(oldInstance.getSubnetId(), newInstance.getSubnetId())) return false;
        if (notEqual(oldInstance.getVpcId(), newInstance.getVpcId())) return false;
        if (notEqual(oldInstance.getPrivateIpAddress(), newInstance.getPrivateIpAddress())) return false;
        if (notEqual(oldInstance.getPublicIpAddress(), newInstance.getPublicIpAddress())) return false;
        if (notEqual(oldInstance.getStateReason(), newInstance.getStateReason())) return false;
        if (notEqual(oldInstance.getArchitecture(), newInstance.getArchitecture())) return false;
        if (notEqual(oldInstance.getRootDeviceType(), newInstance.getRootDeviceType())) return false;
        if (notEqual(oldInstance.getRootDeviceName(), newInstance.getRootDeviceName())) return false;
        if (notEqualCollection(oldInstance.getBlockDeviceMappings(), newInstance.getBlockDeviceMappings())) return false;
        if (notEqual(oldInstance.getVirtualizationType(), newInstance.getVirtualizationType())) return false;
        if (notEqual(oldInstance.getInstanceLifecycle(), newInstance.getInstanceLifecycle())) return false;
        if (notEqual(oldInstance.getSpotInstanceRequestId(), newInstance.getSpotInstanceRequestId())) return false;
        if (notEqual(oldInstance.getLicense(), newInstance.getLicense())) return false;
        if (notEqual(oldInstance.getClientToken(), newInstance.getClientToken())) return false;
        if (notEqualCollection(oldInstance.getTags(), newInstance.getTags())) return false;
        if (notEqualCollection(oldInstance.getSecurityGroups(), newInstance.getSecurityGroups())) return false;
        if (notEqual(oldInstance.getSourceDestCheck(), newInstance.getSourceDestCheck())) return false;
        if (notEqual(oldInstance.getHypervisor(), newInstance.getHypervisor())) return false;
        if (notEqualNetworkInterfaces(oldInstance.getNetworkInterfaces(), newInstance.getNetworkInterfaces())) return false;
        if (notEqual(oldInstance.getIamInstanceProfile(), newInstance.getIamInstanceProfile())) return false;
        if (notEqual(oldInstance.getEbsOptimized(), newInstance.getEbsOptimized())) return false;
        if (notEqual(this.getTerminationProtection(), newEc2Instance.getTerminationProtection())) return false;
        if (notEqual(this.getShutdownBehavior(), newEc2Instance.getShutdownBehavior())) return false;
        if (notEqual(this.getUserData(), newEc2Instance.getUserData())) return false;

        return true;
    }

    public boolean notEqualNetworkInterfaces(List<InstanceNetworkInterface> l1, List<InstanceNetworkInterface> l2) {
        for (InstanceNetworkInterface ini1 : l1) {
            sortInstanceNetworkInterface(ini1);
        }
        for (InstanceNetworkInterface ini2 : l2) {
            sortInstanceNetworkInterface(ini2);
        }
        return notEqualCollection(l1, l2);
    }

    public static void sortInstanceNetworkInterface(InstanceNetworkInterface ini) {
        Collections.sort(ini.getGroups(), new GroupIdentifierComparator());
        Collections.sort(ini.getPrivateIpAddresses(), new InstancePrivateIpAddressComparator());
    }

    public Boolean getTerminationProtection() {
        return terminationProtection;
    }

    public void setTerminationProtection(Boolean terminationProtection) {
        this.terminationProtection = terminationProtection;
    }

    public String getShutdownBehavior() {
        return shutdownBehavior;
    }

    public void setShutdownBehavior(String shutdownBehavior) {
        this.shutdownBehavior = shutdownBehavior;
    }

    public String getReservationId() {
        return reservationId;
    }

    public void setReservationId(String reservationId) {
        this.reservationId = reservationId;
    }

    public String getUserData() {
        return userData;
    }

    public void setUserData(String userData) {
        this.userData = userData;
    }
}