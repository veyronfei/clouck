package com.clouck.comparator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.amazonaws.services.ec2.model.CreateVolumePermission;
import com.amazonaws.services.ec2.model.Snapshot;
import com.clouck.model.Event;
import com.clouck.model.EventType;
import com.clouck.model.aws.ec2.Ec2Snapshot;
import com.clouck.util.ResourceUtil;

@Component
public class Ec2SnapshotComparator extends AbstractEc2Comparator<Ec2Snapshot> {
    private static final Logger log = LoggerFactory.getLogger(Ec2SnapshotComparator.class);

    @Autowired
    private ResourceUtil resourceUtil;

    @Override
    public Event firstScan() {
        return createFirstScanEvent(EventType.Ec2_Snapshot_First_Scan);
    }

    @Override
    public Event initialise(Ec2Snapshot newResource) {
        return createEvent(null, newResource, EventType.Ec2_Snapshot_Found);
    }

    @Override
    public Event add(Ec2Snapshot newResource) {
        Snapshot newSnapshot = newResource.getResource();
        String state = newSnapshot.getState();
        switch (state) {
        case "pending":
            return createEvent(null, newResource, EventType.Ec2_Snapshot_Pending);
        case "completed":
            return createEvent(null, newResource, EventType.Ec2_Snapshot_Created);
        default:
            log.error("not handled instance state:{}", state);
            return createEvent(null, newResource, EventType.Unknown);
        }
    }

    @Override
    protected void update(List<Event> result, Ec2Snapshot oldResource, Ec2Snapshot newResource) {
        Snapshot oldSnapshot = oldResource.getResource();
        Snapshot newSnapshot = newResource.getResource();

        compareSnapshotState(result, oldResource, newResource);
        compareCreateVolumePermission(result, oldResource.getCreateVolumePermissions(), newResource.getCreateVolumePermissions(), oldResource, newResource);
        compareTags(result, oldSnapshot.getTags(), newSnapshot.getTags(), oldResource, newResource);
    }

    @Override
    public Event delete(Ec2Snapshot oldResource) {
        return createEvent(oldResource, null, EventType.Ec2_Snapshot_Deleted);
    }

    private void compareSnapshotState(Collection<Event> result, Ec2Snapshot oldResource, Ec2Snapshot newResource) {
        Snapshot newInstance = newResource.getResource();
        Snapshot oldInstance = oldResource.getResource();
        String newState = newInstance.getState();
        String oldState = oldInstance.getState();
        if (notEqual(newState, oldState)) {
            switch (newState) {
            case "pending":
                result.add(createEvent(oldResource, newResource, EventType.Ec2_Snapshot_Pending));
                break;
            case "completed":
                result.add(createEvent(oldResource, newResource, EventType.Ec2_Snapshot_Created));
                break;
            default:
                log.error("not handled instance state:" + newState);
                result.add(createEvent(oldResource, newResource, EventType.Unknown));
            }
        }
    }

    private void compareCreateVolumePermission(Collection<Event> result, List<CreateVolumePermission> oldPermissions, List<CreateVolumePermission> newPermissions,
            Ec2Snapshot oldResource, Ec2Snapshot newResource) {
        Boolean oldIsPublic = isPublic(result, oldResource, newResource, oldPermissions);
        Boolean newIsPublic = isPublic(result, oldResource, newResource, newPermissions);
        if (notEqual(oldIsPublic, newIsPublic)) {
            if (oldIsPublic != null && newIsPublic != null) {
                if (newIsPublic) {
                    result.add(createEvent(oldResource, newResource, EventType.Ec2_Snapshot_Permission_Visibility_Public));
                } else {
                    result.add(createEvent(oldResource, newResource, EventType.Ec2_Snapshot_Permission_Visibility_Private));
                }
            }
        }

        CompareResult<CreateVolumePermission> compareResult = resourceUtil.compare(removeGroup(oldPermissions), removeGroup(newPermissions));
        for (CreateVolumePermission cvp : compareResult.getAdd()) {
            result.add(createEvent(oldResource, newResource, EventType.Ec2_Snapshot_Permission_Account_Added, cvp.getUserId()));
        }
        for (CreateVolumePermission cvp : compareResult.getDelete()) {
            result.add(createEvent(oldResource, newResource, EventType.Ec2_Snapshot_Permission_Account_Removed, cvp.getUserId()));
        }
        for (Pair<CreateVolumePermission, CreateVolumePermission> pair : compareResult.getUpdate()) {
            log.error("not handled case");
            result.add(createEvent(oldResource, newResource, EventType.Unknown));
        }
    }

    private Boolean isPublic(Collection<Event> result, Ec2Snapshot oldResource, Ec2Snapshot newResource, List<CreateVolumePermission> permissions) {
        Validate.notNull(permissions);
        for (CreateVolumePermission cvp : permissions) {
            if (cvp.getGroup() != null) {
                if (cvp.getGroup().equals("all")) {
                    return true;
                } else {
                    log.error("not handled group:{}", cvp.getGroup());
                    result.add(createEvent(oldResource, newResource, EventType.Unknown));
                    return null;
                }
            }
        }
        return false;
    }

    private List<CreateVolumePermission> removeGroup(List<CreateVolumePermission> permissions) {
        List<CreateVolumePermission> result = new ArrayList<>();
        for (CreateVolumePermission cvp : permissions) {
            if (cvp.getGroup() == null) {
                result.add(cvp);
            }
        }
        return result;
    }
}
