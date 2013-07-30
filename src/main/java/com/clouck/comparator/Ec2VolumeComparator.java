package com.clouck.comparator;

import java.util.Collection;
import java.util.List;

import org.apache.commons.lang.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.amazonaws.services.ec2.model.Volume;
import com.amazonaws.services.ec2.model.VolumeAttachment;
import com.clouck.exception.ClouckUnexpectedConditionException;
import com.clouck.model.Event;
import com.clouck.model.EventType;
import com.clouck.model.aws.ec2.Ec2Volume;

@Component
public class Ec2VolumeComparator extends AbstractEc2Comparator<Ec2Volume> {
    private static final Logger log = LoggerFactory.getLogger(Ec2VolumeComparator.class);

    @Override
    public Event firstScan() {
        return createFirstScanEvent(EventType.Ec2_Volume_First_Scan);
    }

    @Override
    public Event initialise(Ec2Volume newResource) {
        return createEvent(null, newResource, EventType.Ec2_Volume_Found);
    }

    @Override
    public Event add(Ec2Volume newResource) {
        Volume volume = newResource.getResource();
        String state = volume.getState();
        switch (state) {
        case "creating":
            return createEvent(null, newResource, EventType.Ec2_Volume_Creating);
        case "available":
        case "in-use":
            return createEvent(null, newResource, EventType.Ec2_Volume_Create);
        case "deleting":
            return createEvent(null, newResource, EventType.Ec2_Volume_Deleting);
        case "error":
            return createEvent(null, newResource, EventType.Ec2_Volume_Error);
        default:
            log.error("not handled instance state:{}", state);
            return createEvent(null, newResource, EventType.Unknown);
        }
    }

    @Override
    public Event delete(Ec2Volume oldResource) {
        return createEvent(oldResource, null, EventType.Ec2_Volume_Delete);
    }

    @Override
    protected void update(List<Event> result, Ec2Volume oldResource, Ec2Volume newResource) {
        Volume oldVolume = oldResource.getResource();
        Volume newVolume = newResource.getResource();
        Validate.isTrue(oldVolume.getAttachments().size() < 2);
        Validate.isTrue(newVolume.getAttachments().size() < 2);

        if (notEqual(oldResource.getAutoEnableIO(), newResource.getAutoEnableIO())) {
            result.add(createEvent(oldResource, newResource, newResource.getAutoEnableIO() ?
                    EventType.Ec2_Volume_Auto_Enable_IO_Enabled : EventType.Ec2_Volume_Auto_Enable_IO_Disabled));
        }
        compareVolumeState(result, oldResource, newResource);
        compareVolumeAttachment(result, oldResource, newResource);
        compareTags(result, oldVolume.getTags(), newVolume.getTags(), oldResource, newResource);
    }

    private void compareVolumeState(Collection<Event> result, Ec2Volume oldResource, Ec2Volume newResource) {
        Volume oldVolume = oldResource.getResource();
        Volume newVolume = newResource.getResource();
        String oldState = oldVolume.getState();
        String newState = newVolume.getState();
        if (equal(newState, oldState)) {
            return;
        }
        switch (newState) {
        case "deleting":
            result.add(createEvent(oldResource, newResource, EventType.Ec2_Volume_Deleting));
            break;
        case "creating":
        case "available":
        case "in-use":
        case "error":
            break;
        default:
            log.error("not handled volume state:{}", newState);
            result.add(createEvent(oldResource, newResource, EventType.Unknown));
        }
    }

    private void compareVolumeAttachment(Collection<Event> result, Ec2Volume oldResource, Ec2Volume newResource) {
        Volume oldVolume = oldResource.getResource();
        Volume newVolume = newResource.getResource();

        List<VolumeAttachment> oldVolumeAttachments = oldVolume.getAttachments();
        List<VolumeAttachment> newVolumeAttachments = newVolume.getAttachments();
        if (oldVolumeAttachments.size() == 0) {
            if (newVolumeAttachments.size() != 0) {
                VolumeAttachment newVolumeAttachment = newVolumeAttachments.get(0);
                result.add(createEvent(oldResource, newResource, EventType.Ec2_Volume_Attached, newVolumeAttachment.getInstanceId()));
            }
        } else {
            if (newVolumeAttachments.size() == 0) {
                result.add(createEvent(oldResource, newResource, EventType.Ec2_Volume_Detached));
            } else {
                VolumeAttachment oldVolumeAttachment = oldVolumeAttachments.get(0);
                VolumeAttachment newVolumeAttachment = newVolumeAttachments.get(0);
                Validate.isTrue(oldVolumeAttachment.getVolumeId().equals(newVolumeAttachment.getVolumeId()));
                if (notEqual(oldVolumeAttachment.getInstanceId(), newVolumeAttachment.getInstanceId())) {
                    result.add(createEvent(oldResource, newResource, EventType.Ec2_Volume_Detached, oldVolumeAttachment.getInstanceId()));
                    result.add(createEvent(oldResource, newResource, EventType.Ec2_Volume_Attached, newVolumeAttachment.getInstanceId()));
                }
                if (notEqual(oldVolumeAttachment.getDevice(), newVolumeAttachment.getDevice())) {
                    result.add(createEvent(oldResource, newResource, EventType.Ec2_Volume_Device, oldVolumeAttachment.getDevice(), newVolumeAttachment.getDevice()));
                }
                if (notEqual(oldVolumeAttachment.getState(), newVolumeAttachment.getState())) {
                    switch (newVolumeAttachment.getState()) {
                    case "attaching":
                        result.add(createEvent(oldResource, newResource, EventType.Ec2_Volume_Attaching, newVolumeAttachment.getInstanceId()));
                        break;
                    case "attached":
                        result.add(createEvent(oldResource, newResource, EventType.Ec2_Volume_Attached, newVolumeAttachment.getInstanceId()));
                        break;
                    case "detaching":
                        result.add(createEvent(oldResource, newResource, EventType.Ec2_Volume_Detaching));
                        break;
                    case "detached":
                        result.add(createEvent(oldResource, newResource, EventType.Ec2_Volume_Detached));
                        break;
                    default:
                        log.error("not handled volume attachment state:{}", newVolumeAttachment.getState());
                        result.add(createEvent(oldResource, newResource, EventType.Unknown));
                    }
                }
                //ignore attach time, as no one care
                if (notEqual(oldVolumeAttachment.getDeleteOnTermination(), newVolumeAttachment.getDeleteOnTermination())) {
                    if (newVolumeAttachment.getDeleteOnTermination()) {
                        result.add(createEvent(oldResource, newResource, EventType.Ec2_Volume_Delete_On_Termination_Enabled));
                    } else {
                        result.add(createEvent(oldResource, newResource, EventType.Ec2_Volume_Delete_On_Termination_Disabled));
                    }
                }
            }
        }
    }
}
