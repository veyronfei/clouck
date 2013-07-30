package com.clouck.comparator;

import java.util.Collection;
import java.util.List;

import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.amazonaws.services.ec2.model.GroupIdentifier;
import com.amazonaws.services.ec2.model.Instance;
import com.amazonaws.services.ec2.model.InstanceNetworkInterface;
import com.amazonaws.services.ec2.model.InstancePrivateIpAddress;
import com.clouck.model.Event;
import com.clouck.model.EventType;
import com.clouck.model.aws.ec2.Ec2Instance;
@Component
public class Ec2InstanceComparator extends AbstractEc2Comparator<Ec2Instance> {
    private static final Logger log = LoggerFactory.getLogger(Ec2InstanceComparator.class);

    @Override
    public Event initialise(Ec2Instance newResource) {
        return createEvent(null, newResource, EventType.Ec2_Instance_Found);
    }

    @Override
    public Event firstScan() {
        return createFirstScanEvent(EventType.Ec2_Instance_First_Scan);
    }

    @Override
    public Event add(Ec2Instance newResource) {
        Instance instance = newResource.getResource();
        String state = instance.getState().getName();
        switch (state) {
        case "pending":
            return createEvent(null, newResource, EventType.Ec2_Instance_Pending);
        case "shutting-down":
            return createEvent(null, newResource, EventType.Ec2_Instance_Shutting_Down);
        case "stopping":
            return createEvent(null, newResource, EventType.Ec2_Instance_Stopping);
        case "running":
            return createEvent(null, newResource, EventType.Ec2_Instance_Launch);
        case "stopped":
            return createEvent(null, newResource, EventType.Ec2_Instance_Stop);
        default:
            log.error("not handled instance state:{}", state);
            return createEvent(null, newResource, EventType.Unknown);
        }
    }

    @Override
    public Event delete(Ec2Instance oldResource) {
        return createEvent(oldResource, null, EventType.Ec2_Instance_Terminate);
    }

    @Override
    protected void update(List<Event> result, Ec2Instance oldResource, Ec2Instance newResource) {
        Instance oldInstance = oldResource.getResource();
        Instance newInstance = newResource.getResource();

        compareInstanceState(result, oldResource, newResource);
        compareSecurityGroups(result, oldInstance.getSecurityGroups(), newInstance.getSecurityGroups(), oldResource, newResource);
        if (notEqual(oldInstance.getSourceDestCheck(), newInstance.getSourceDestCheck())) {
            //during shutting down, before terminated, this source dest check is null which causes NPE
            if (newInstance.getSourceDestCheck() != null) {
                result.add(createEvent(oldResource, newResource, newInstance.getSourceDestCheck() ?
                        EventType.Ec2_Instance_Source_Dest_Check_Enabled : EventType.Ec2_Instance_Source_Dest_Check_Disabled));
            } else {
                log.error("not handled null source dst check");
                result.add(createEvent(oldResource, newResource, EventType.Unknown));
            }
        }
        compareNetworkInterfaces(result, oldInstance.getNetworkInterfaces(), newInstance.getNetworkInterfaces(), oldResource, newResource);
        if (notEqual(oldInstance.getMonitoring().getState(), newInstance.getMonitoring().getState())) {
            if (newInstance.getMonitoring().getState().equals("enabled")) {
                result.add(createEvent(oldResource, newResource, EventType.Ec2_Instance_Monitoring_Enabled));
            } else if (newInstance.getMonitoring().getState().equals("disabled"))  {
                result.add(createEvent(oldResource, newResource, EventType.Ec2_Instance_Monitoring_Disabled));
            } else {
                log.error("not handled monitoring state:{}", newInstance.getMonitoring().getState());
                result.add(createEvent(oldResource, newResource, EventType.Unknown));
            }
        }
        if (notEqual(oldResource.getTerminationProtection(), newResource.getTerminationProtection())) {
            if (newResource.getTerminationProtection() != null) {
                result.add(createEvent(oldResource, newResource, newResource.getTerminationProtection() ?
                        EventType.Ec2_Instance_Termination_Protection_Enabled : EventType.Ec2_Instance_Termination_Protection_Disabled));
            } else {
                log.error("not handled null termination protection");
                result.add(createEvent(oldResource, newResource, EventType.Unknown));
            }
        }
        if (notEqual(oldResource.getShutdownBehavior(), newResource.getShutdownBehavior())) {
            if (newResource.getShutdownBehavior().equals("stop")) {
                result.add(createEvent(oldResource, newResource, EventType.Ec2_Instance_Shutdown_Behavior_Stop));
            } else if (newResource.getShutdownBehavior().equals("terminate"))  {
                result.add(createEvent(oldResource, newResource, EventType.Ec2_Instance_Shutdown_Behavior_Terminate));
            } else {
                log.error("not handled shut down behavior:{}", newResource.getShutdownBehavior());
                result.add(createEvent(oldResource, newResource, EventType.Unknown));
            }
        }
        if (notEqual(oldInstance.getInstanceType(), newInstance.getInstanceType())) {
            result.add(createEvent(oldResource, newResource, EventType.Ec2_Instance_Instance_Type, oldInstance.getInstanceType(), newInstance.getInstanceType()));
        }
        if (notEqual(oldInstance.getEbsOptimized(), newInstance.getEbsOptimized())) {
            if (newInstance.getEbsOptimized() != null) {
                result.add(createEvent(oldResource, newResource, newInstance.getEbsOptimized() ? EventType.Ec2_Instance_EBS_Optimized_Enabled : EventType.Ec2_Instance_EBS_Optimized_Disabled));
            } else {
                log.error("not handled null ebs optimized");
                result.add(createEvent(oldResource, newResource, EventType.Unknown));
            }
        }
        if (notEqual(oldResource.getUserData(), newResource.getUserData())) {
            result.add(createEvent(oldResource, newResource, EventType.Ec2_Instance_User_Data_Changed));
        }
        compareTags(result, oldInstance.getTags(), newInstance.getTags(), oldResource, newResource);
    }

    private void compareNetworkInterfaces(Collection<Event> result, List<InstanceNetworkInterface> oldNetworkInterfaces, List<InstanceNetworkInterface> newNetworkInterfaces,
            Ec2Instance oldResource, Ec2Instance newResource) {
        CompareResult<InstanceNetworkInterface> compareResult = resourceUtil.compareNetworkInterfaces(oldNetworkInterfaces, newNetworkInterfaces);
        for (Pair<InstanceNetworkInterface, InstanceNetworkInterface> pair : compareResult.getUpdate()) {
            InstanceNetworkInterface oldINI = pair.getLeft();
            InstanceNetworkInterface newINI = pair.getRight();
            if (notEqual(oldINI.getAssociation(), newINI.getAssociation())) {
                if (newINI.getAssociation() == null) {
                    result.add(createEvent(oldResource, newResource, EventType.Ec2_Instance_Elastic_Ip_Disassociated, oldINI.getAssociation().getPublicIp(), newINI.getNetworkInterfaceId()));
                } else {
                    log.error("not handled association:{}", newINI.getAssociation());
                    result.add(createEvent(oldResource, newResource, EventType.Unknown));
                }
            }
            if (notEqual(oldINI.getAttachment().getStatus(), newINI.getAttachment().getStatus())) {
                generateEvents4Attachment(result, newINI, oldResource, newResource);
            }
            CompareResult<InstancePrivateIpAddress> compareResultIPIA = resourceUtil.comparePrivateIpAddresses(oldINI.getPrivateIpAddresses(), newINI.getPrivateIpAddresses());
            for (InstancePrivateIpAddress ipi : compareResultIPIA.getAdd()) {
                result.add(createEvent(oldResource, newResource, EventType.Ec2_Instance_Private_Ip_Assigned, ipi.getPrivateIpAddress(), newINI.getNetworkInterfaceId()));
            }
            for (InstancePrivateIpAddress ipi : compareResultIPIA.getDelete()) {
                result.add(createEvent(oldResource, newResource, EventType.Ec2_Instance_Private_Ip_Unassigned, ipi.getPrivateIpAddress(), newINI.getNetworkInterfaceId()));
            }
            for (Pair<InstancePrivateIpAddress, InstancePrivateIpAddress> p : compareResultIPIA.getUpdate()) {
                log.error("not handled case");
                result.add(createEvent(oldResource, newResource, EventType.Unknown));
            }
            if (equal(oldINI.getAssociation(), newINI.getAssociation()) && equal(oldINI.getAttachment().getStatus(), newINI.getAttachment().getStatus()) &&
                    compareResultIPIA.getAdd().size() == 0 && compareResultIPIA.getDelete().size() == 0 && compareResultIPIA.getUpdate().size() == 0) {
                log.error("not handled case");
                result.add(createEvent(oldResource, newResource, EventType.Unknown));
            }
        }
        for (InstanceNetworkInterface ini : compareResult.getAdd()) {
            generateEvents4Attachment(result, ini, oldResource, newResource);
        }
        for (InstanceNetworkInterface ini : compareResult.getDelete()) {
            result.add(createEvent(oldResource, newResource, EventType.Ec2_Instance_Network_Interface_Detached, ini.getNetworkInterfaceId()));
        }
    }

    private void generateEvents4Attachment(Collection<Event> result, InstanceNetworkInterface ini, Ec2Instance oldResource, Ec2Instance newResource) {
        String status = ini.getAttachment().getStatus();
        if (status.equals("attaching")) {
            result.add(createEvent(oldResource, newResource, EventType.Ec2_Instance_Network_Interface_Attaching, ini.getNetworkInterfaceId()));
        } else if (status.equals("attached")) {
            result.add(createEvent(oldResource, newResource, EventType.Ec2_Instance_Network_Interface_Attached, ini.getNetworkInterfaceId()));
        } else if (status.equals("detaching")) {
            result.add(createEvent(oldResource, newResource, EventType.Ec2_Instance_Network_Interface_Detaching, ini.getNetworkInterfaceId()));
        } else {
            log.error("not handled attachment status:{}", status);
            result.add(createEvent(oldResource, newResource, EventType.Unknown));
        }
    }

    private void compareSecurityGroups(Collection<Event> result, List<GroupIdentifier> oldSecurityGroups, List<GroupIdentifier> newSecurityGroups,
            Ec2Instance oldResource, Ec2Instance newResource) {
        CompareResult<GroupIdentifier> compareResult = resourceUtil.compare(oldSecurityGroups, newSecurityGroups);
        for (GroupIdentifier gi : compareResult.getAdd()) {
            result.add(createEvent(oldResource, newResource, EventType.Ec2_Instance_Security_Group_Added, gi.getGroupName()));
        }
        for (GroupIdentifier gi : compareResult.getDelete()) {
            result.add(createEvent(oldResource, newResource, EventType.Ec2_Instance_Security_Group_Deleted, gi.getGroupName()));
        }
        for (Pair<GroupIdentifier, GroupIdentifier> pair : compareResult.getUpdate()) {
            log.error("not handled case");
            result.add(createEvent(oldResource, newResource, EventType.Unknown));
        }
    }

    private void compareInstanceState(Collection<Event> result, Ec2Instance oldResource, Ec2Instance newResource) {
        Instance newInstance = newResource.getResource();
        Instance oldInstance = oldResource.getResource();
        String newState = newInstance.getState().getName();
        String oldState = oldInstance.getState().getName();
        if (notEqual(newState, oldState)) {
            switch (newState) {
            case "pending":
                result.add(createEvent(oldResource, newResource, EventType.Ec2_Instance_Pending));
                break;
            case "shutting-down":
                result.add(createEvent(oldResource, newResource, EventType.Ec2_Instance_Shutting_Down));
                break;
            case "stopping":
                result.add(createEvent(oldResource, newResource, EventType.Ec2_Instance_Stopping));
                break;
            case "terminated":
                result.add(createEvent(oldResource, newResource, EventType.Ec2_Instance_Terminate));
                break;
            case "running":
                result.add(createEvent(oldResource, newResource, EventType.Ec2_Instance_Launch));
                break;
            case "stopped":
                result.add(createEvent(oldResource, newResource, EventType.Ec2_Instance_Stop));
                break;
            default:
                log.error("not handled instance state:" + newState);
                result.add(createEvent(oldResource, newResource, EventType.Unknown));
            }
        }
    }
}
