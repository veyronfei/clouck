package com.clouck.comparator;

import static com.clouck.util.ResourceUtil.notEqualCollection;

import java.util.Collection;
import java.util.List;

import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.amazonaws.services.ec2.model.BlockDeviceMapping;
import com.amazonaws.services.ec2.model.Image;
import com.amazonaws.services.ec2.model.LaunchPermission;
import com.clouck.model.Event;
import com.clouck.model.EventType;
import com.clouck.model.aws.ec2.Ec2Ami;

@Component
public class Ec2AmiComparator extends AbstractEc2Comparator<Ec2Ami> {
    private static final Logger log = LoggerFactory.getLogger(Ec2AmiComparator.class);

    @Override
    public Event firstScan() {
        return createFirstScanEvent(EventType.Ec2_AMI_First_Scan);
    }

    @Override
    public Event initialise(Ec2Ami newResource) {
        return createEvent(null, newResource, EventType.Ec2_AMI_Found);
    }

    @Override
    public Event add(Ec2Ami newResource) {
        Image image = newResource.getResource();
        String state = image.getState();
        switch (state) {
        case "available":
            return createEvent(null, newResource, EventType.Ec2_AMI_Registered);
        case "deregistered":
            return createEvent(null, newResource, EventType.Ec2_AMI_Deregistered);
        case "pending":
            return createEvent(null, newResource, EventType.Ec2_AMI_Pending);
        default:
            log.error("not handled instance state:{}", state);
            return createEvent(null, newResource, EventType.Unknown);
        }
    }

    @Override
    public Event delete(Ec2Ami oldResource) {
        return createEvent(oldResource, null, EventType.Ec2_AMI_Deregistered);
    }

    @Override
    protected void update(List<Event> result, Ec2Ami oldResource, Ec2Ami newResource) {
        Image oldImage = oldResource.getResource();
        Image newImage = newResource.getResource();
        if (notEqual(oldImage.isPublic(), newImage.isPublic())) {
            result.add(createEvent(oldResource, newResource, newImage.isPublic() ? EventType.Ec2_AMI_Permission_Visibility_Public : EventType.Ec2_AMI_Permission_Visibility_Private));
        }
        if (notEqual(oldImage.getDescription(), newImage.getDescription())) {
            result.add(createEvent(oldResource, newResource, EventType.Ec2_AMI_Description_Changed, oldImage.getDescription(), newImage.getDescription()));
        }
        if (notEqual(oldImage.getState(), newImage.getState())) {
            switch (newImage.getState()) {
            case "available":
                result.add(createEvent(oldResource, newResource, EventType.Ec2_AMI_Registered));
                break;
            default:
                log.error("not handled instance state:{}", newImage.getState());
                result.add(createEvent(oldResource, newResource, EventType.Unknown));
            }
        }
        if (notEqualCollection(oldImage.getBlockDeviceMappings(), newImage.getBlockDeviceMappings())) {
            
        }
        compareBlockDeviceMapping(result, oldImage.getBlockDeviceMappings(), newImage.getBlockDeviceMappings(), oldResource, newResource);
        compareLaunchPermission(result, oldResource.getLaunchPermissions(), newResource.getLaunchPermissions(), oldResource, newResource);
        compareTags(result, oldImage.getTags(), newImage.getTags(), oldResource, newResource);
    }

    private void compareBlockDeviceMapping(Collection<Event> result, List<BlockDeviceMapping> oldBlockDeviceMappings, List<BlockDeviceMapping> newBlockDeviceMappings,
            Ec2Ami oldResource, Ec2Ami newResource) {
        CompareResult<BlockDeviceMapping> compareResult = resourceUtil.compareBlockDeviceMappings(oldBlockDeviceMappings, newBlockDeviceMappings);
        for (BlockDeviceMapping bm : compareResult.getAdd()) {
            log.error("not handled case");
            result.add(createEvent(oldResource, newResource, EventType.Unknown));
        }
        for (BlockDeviceMapping bm : compareResult.getDelete()) {
            log.error("not handled case");
            result.add(createEvent(oldResource, newResource, EventType.Unknown));
        }
        for (Pair<BlockDeviceMapping, BlockDeviceMapping> pair : compareResult.getUpdate()) {
            String oldSnapshotId = pair.getLeft().getEbs().getSnapshotId();
            String newSnapshotId = pair.getRight().getEbs().getSnapshotId();
            if (notEqual(oldSnapshotId, newSnapshotId)) {
                if (oldSnapshotId == null && newSnapshotId != null) {
                    result.add(createEvent(oldResource, newResource, EventType.Ec2_AMI_Snapshot_Completed, newSnapshotId));
                }
            } else {
                log.error("not handled case");
                result.add(createEvent(oldResource, newResource, EventType.Unknown));
            }
        }
    }

    private void compareLaunchPermission(Collection<Event> result, List<LaunchPermission> oldPermissions, List<LaunchPermission> newPermissions,
            Ec2Ami oldResource, Ec2Ami newResource) {
        CompareResult<LaunchPermission> compareResult = resourceUtil.compare(oldPermissions, newPermissions);
        for (LaunchPermission lp : compareResult.getAdd()) {
            result.add(createEvent(oldResource, newResource, EventType.Ec2_AMI_Permission_Account_Added, lp.getUserId()));
        }
        for (LaunchPermission lp : compareResult.getDelete()) {
            result.add(createEvent(oldResource, newResource, EventType.Ec2_AMI_Permission_Account_Removed, lp.getUserId()));
        }
        for (Pair<LaunchPermission, LaunchPermission> pair : compareResult.getUpdate()) {
            log.error("not handled case");
            result.add(createEvent(oldResource, newResource, EventType.Unknown));
        }
    }
}