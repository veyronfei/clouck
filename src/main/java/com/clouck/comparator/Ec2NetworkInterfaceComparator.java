package com.clouck.comparator;

import java.util.Collection;
import java.util.List;

import org.springframework.stereotype.Component;

import com.amazonaws.services.ec2.model.GroupIdentifier;
import com.amazonaws.services.ec2.model.NetworkInterface;
import com.clouck.exception.ClouckUnexpectedConditionException;
import com.clouck.model.Event;
import com.clouck.model.EventType;
import com.clouck.model.aws.ec2.Ec2NetworkInterface;

@Component
public class Ec2NetworkInterfaceComparator extends AbstractEc2Comparator<Ec2NetworkInterface> {

    @Override
    public Event firstScan() {
        return createFirstScanEvent(EventType.Ec2_Network_Interface_First_Scan);
    }

    @Override
    public Event initialise(Ec2NetworkInterface newResource) {
        return createEvent(null, newResource, EventType.Ec2_Network_Interface_Found);
    }

    @Override
    public Event add(Ec2NetworkInterface newResource) {
        return createEvent(null, newResource, EventType.Ec2_Network_Interface_Created);
    }

    @Override
    protected void update(List<Event> result, Ec2NetworkInterface oldResource, Ec2NetworkInterface newResource) {
        NetworkInterface oldNI = oldResource.getResource();
        NetworkInterface newNI = newResource.getResource();
        if (notEqual(oldNI.getStatus(), newNI.getStatus())) {
            if (newNI.getStatus().equals("available")) {
                result.add(createEvent(oldResource, newResource, EventType.Ec2_Network_Interface_Detached));
            } else if (newNI.getStatus().equals("in-use")) {
                result.add(createEvent(oldResource, newResource, EventType.Ec2_Network_Interface_Attached, newNI.getAttachment().getInstanceId()));
            } else {
                throw new ClouckUnexpectedConditionException("status should only be available or in-use, but got:" + newNI.getStatus());
            }
        }
        if (notEqual(oldNI.getSourceDestCheck(), newNI.getSourceDestCheck())) {
            result.add(createEvent(oldResource, newResource, newNI.getSourceDestCheck() ? EventType.Ec2_Network_Interface_Source_Dest_Check_Enabled : EventType.Ec2_Network_Interface_Source_Dest_Check_Disabled));
        }
        if (notEqual(oldNI.getDescription(), newNI.getDescription())) {
            result.add(createEvent(oldResource, newResource, EventType.Ec2_Network_Interface_Description_Updated, newNI.getDescription()));
        }
        compareSecurityGroups(result, oldNI.getGroups(), newNI.getGroups(), oldResource, newResource);
//        compareNetworkInterfaceSecondaryPrivateIpAddress(result, oldNI.getPrivateIpAddresses(), newNI.getPrivateIpAddresses(), newResource, oldVersion, newVersion);
        compareTags(result, oldNI.getTagSet(), newNI.getTagSet(), oldResource, newResource);
    }

    @Override
    public Event delete(Ec2NetworkInterface oldResource) {
        return createEvent(oldResource, null, EventType.Ec2_Network_Interface_Deleted);
    }

    private void compareSecurityGroups(Collection<Event> result, List<GroupIdentifier> oldSecurityGroups, List<GroupIdentifier> newSecurityGroups,
            Ec2NetworkInterface oldResource, Ec2NetworkInterface newResource) {
        CompareResult<GroupIdentifier> compareResult = resourceUtil.compare(oldSecurityGroups, newSecurityGroups);
        for (GroupIdentifier gi : compareResult.getAdd()) {
            result.add(createEvent(oldResource, newResource, EventType.Ec2_Network_Interface_Security_Group_Added));
        }
        for (GroupIdentifier gi : compareResult.getDelete()) {
            result.add(createEvent(oldResource, newResource, EventType.Ec2_Network_Interface_Security_Group_Deleted));
        }
    }

//    private void compareNetworkInterfaceSecondaryPrivateIpAddress(Collection<Event> result, List<NetworkInterfacePrivateIpAddress> oldNIPIAs,
//            List<NetworkInterfacePrivateIpAddress> newNIPIAs,
//            Ec2NetworkInterface newResource, Ec2Version oldVersion, Ec2Version newVersion) {
//        List<String> oldSecondaryPrivateIpAddresses = new ArrayList<>();
//        List<String> newSecondaryPrivateIpAddresses = new ArrayList<>();
//        for (NetworkInterfacePrivateIpAddress address : oldNIPIAs) {
//            oldSecondaryPrivateIpAddresses.add(address.getPrivateIpAddress());
//        }
//        for (NetworkInterfacePrivateIpAddress address : newNIPIAs) {
//            newSecondaryPrivateIpAddresses.add(address.getPrivateIpAddress());
//        }
//        CompareResult<String> compareResult = compare(oldSecondaryPrivateIpAddresses, newSecondaryPrivateIpAddresses);
//        for (String address : compareResult.getAdd()) {
//            result.add(createEvent(newResource, EventType.Ec2_Network_Interface_Assign, oldVersion, newVersion,
//                    String.format("Assigned secondary private address %s on network interface %s", address, newResource.getResource().getNetworkInterfaceId())));
//        }
//        for (String address : compareResult.getDelete()) {
//            result.add(createEvent(newResource, EventType.Ec2_Network_Interface_Unassign, oldVersion, newVersion,
//                    String.format("Unassigned secondary private address %s on network interface %s", address, newResource.getResource().getNetworkInterfaceId())));
//        }
//    }
}
