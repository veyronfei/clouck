package com.clouck.model.aws.ec2;

import static com.clouck.util.ResourceUtil.notEqual;
import static com.clouck.util.ResourceUtil.notEqualCollection;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

import com.amazonaws.services.ec2.model.Instance;
import com.amazonaws.services.ec2.model.InstanceNetworkInterface;
import com.amazonaws.services.ec2.model.Reservation;
import com.clouck.model.aws.AbstractResource;
import com.clouck.model.aws.comparator.GroupIdentifierComparator;
import com.clouck.model.aws.comparator.InstanceBlockDeviceMappingComparator;
import com.clouck.model.aws.comparator.InstanceNetworkInterfaceComparator;
import com.clouck.model.aws.comparator.InstancePrivateIpAddressComparator;
import com.clouck.model.aws.comparator.ProductCodeComparator;
import com.clouck.model.aws.comparator.TagComparator;

@Document(collection = "ec2_reservation")
@TypeAlias(value = "ec2_reservation")
@SuppressWarnings("serial")
public class Ec2Reservation extends AbstractResource<Reservation> {
    private Map<String, Ec2InstanceAttribute> instanceId2Attributes = new HashMap<>();

    public Map<String, Ec2InstanceAttribute> getInstanceId2Attributes() {
        return instanceId2Attributes;
    }

    public void setInstanceId2Attributes(Map<String, Ec2InstanceAttribute> instanceId2Attributes) {
        this.instanceId2Attributes = instanceId2Attributes;
    }

    @Override
    @SuppressWarnings("rawtypes")
    protected boolean isEqual(AbstractResource newResource) {
        Reservation oldReservation = this.getResource();
        Ec2Reservation newEc2Reservation = (Ec2Reservation) newResource;
        Reservation newReservation = newEc2Reservation.getResource();

        if (notEqual(oldReservation.getRequesterId(), newReservation.getRequesterId())) return false;
        if (notEqual(oldReservation.getOwnerId(), newReservation.getOwnerId())) return false;
        if (notEqual(oldReservation.getRequesterId(), newReservation.getRequesterId())) return false;
        if (notEqualCollection(oldReservation.getGroups(), newReservation.getGroups())) return false;
        if (notEqualCollection(oldReservation.getGroupNames(), newReservation.getGroupNames())) return false;
        if (notEqualInstancess(oldReservation.getInstances(), newReservation.getInstances())) return false;
        if (notEqual(this.getInstanceId2Attributes(), newEc2Reservation.getInstanceId2Attributes())) return false;

        return true;
    }

    private boolean notEqualInstancess(List<Instance> l1, List<Instance> l2) {
        for (Instance i1 : l1) {
            Collections.sort(i1.getProductCodes(), new ProductCodeComparator());
            Collections.sort(i1.getBlockDeviceMappings(), new InstanceBlockDeviceMappingComparator());
            Collections.sort(i1.getTags(), new TagComparator());
            Collections.sort(i1.getSecurityGroups(), new GroupIdentifierComparator());
            Collections.sort(i1.getNetworkInterfaces(), new InstanceNetworkInterfaceComparator());
            for (InstanceNetworkInterface ini1 : i1.getNetworkInterfaces()) {
                Collections.sort(ini1.getGroups(), new GroupIdentifierComparator());
                Collections.sort(ini1.getPrivateIpAddresses(), new InstancePrivateIpAddressComparator());
            }
        }
        for (Instance i2 : l2) {
            Collections.sort(i2.getProductCodes(), new ProductCodeComparator());
            Collections.sort(i2.getBlockDeviceMappings(), new InstanceBlockDeviceMappingComparator());
            Collections.sort(i2.getTags(), new TagComparator());
            Collections.sort(i2.getSecurityGroups(), new GroupIdentifierComparator());
            Collections.sort(i2.getNetworkInterfaces(), new InstanceNetworkInterfaceComparator());
            for (InstanceNetworkInterface ini2 : i2.getNetworkInterfaces()) {
                Collections.sort(ini2.getGroups(), new GroupIdentifierComparator());
                Collections.sort(ini2.getPrivateIpAddresses(), new InstancePrivateIpAddressComparator());
            }
        }
        return notEqualCollection(l1, l2);
    }
}