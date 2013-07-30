package com.clouck.comparator;

import java.util.Collection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.amazonaws.services.ec2.model.PlacementGroup;
import com.clouck.exception.ClouckUnexpectedConditionException;
import com.clouck.model.Event;
import com.clouck.model.EventType;
import com.clouck.model.aws.ec2.Ec2PlacementGroup;
import com.clouck.model.aws.ec2.Ec2Version;

@Component
public class Ec2PlacementGroupComparator extends AbstractEc2Comparator<Ec2PlacementGroup> {
    private static final Logger log = LoggerFactory.getLogger(Ec2PlacementGroupComparator.class);

    @Override
    public Event firstScan() {
        return createFirstScanEvent(EventType.Ec2_PlacementGroup_First_Scan);
    }

    @Override
    public Event initialise(Ec2PlacementGroup newResource) {
        return createEvent(null, newResource, EventType.Ec2_PlacementGroup_Found);
    }

    @Override
    public Event add(Ec2PlacementGroup newResource) {
        PlacementGroup placementGroup = newResource.getResource();
        String state = placementGroup.getState();
        switch (state) {
        case "pending":
            return createEvent(null, newResource, EventType.Ec2_PlacementGroup_Pending);
        case "deleting":
            return createEvent(null, newResource, EventType.Ec2_PlacementGroup_Deleting);
//        case "deleted":
        case "available":
            return createEvent(null, newResource, EventType.Ec2_PlacementGroup_Create);
        default:
            log.error("not handled placement group state:{}", state);
            return createEvent(null, newResource, EventType.Unknown);
        }
    }

    @Override
    public Event delete(Ec2PlacementGroup oldResource) {
        return createEvent(oldResource, null, EventType.Ec2_PlacementGroup_Delete);
    }

    @Override
    protected void update(List<Event> result, Ec2PlacementGroup oldResource, Ec2PlacementGroup newResource) {
        PlacementGroup newPlacementGroup = newResource.getResource();
        PlacementGroup oldPlacementGroup = oldResource.getResource();
        String newState = newPlacementGroup.getState();
        String oldState = oldPlacementGroup.getState();
        if (notEqual(newState, oldState)) {
            switch (newState) {
            case "pending":
                result.add(createEvent(oldResource, newResource, EventType.Ec2_PlacementGroup_Pending));
                break;
            case "deleting":
                result.add(createEvent(oldResource, newResource, EventType.Ec2_PlacementGroup_Deleting));
//        case "deleted":
                break;
            case "available":
                result.add(createEvent(oldResource, newResource, EventType.Ec2_PlacementGroup_Create));
                break;
            default:
                log.error("not handled placement group state:{}", newState);
                result.add(createEvent(oldResource, newResource, EventType.Unknown));
            }
        }
    }
}
