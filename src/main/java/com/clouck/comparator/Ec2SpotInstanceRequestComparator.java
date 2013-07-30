package com.clouck.comparator;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.amazonaws.services.ec2.model.SpotInstanceRequest;
import com.clouck.model.Event;
import com.clouck.model.EventType;
import com.clouck.model.aws.ec2.Ec2SpotInstanceRequest;

@Component
public class Ec2SpotInstanceRequestComparator extends AbstractEc2Comparator<Ec2SpotInstanceRequest> {
    private static final Logger log = LoggerFactory.getLogger(Ec2SpotInstanceRequestComparator.class);

    @Override
    public Event firstScan() {
        return createFirstScanEvent(EventType.Ec2_Spot_Instance_Request_First_Scan);
    }

    @Override
    public Event initialise(Ec2SpotInstanceRequest newResource) {
        return createEvent(null, newResource, EventType.Ec2_Spot_Instance_Request_Found);
    }

    @Override
    public Event add(Ec2SpotInstanceRequest newResource) {
        return createEvent(null, newResource, EventType.Ec2_Spot_Instance_Request_Created);
    }

    @Override
    public Event delete(Ec2SpotInstanceRequest oldResource) {
        return createEvent(oldResource, null, EventType.Ec2_Spot_Instance_Request_Cancelled);
    }

    @Override
    protected void update(List<Event> result, Ec2SpotInstanceRequest oldResource, Ec2SpotInstanceRequest newResource) {
        SpotInstanceRequest oldReq = oldResource.getResource();
        SpotInstanceRequest newReq = newResource.getResource();

        if (notEqual(oldReq.getState(), newReq.getState())) {
            switch (newReq.getState()) {
            case "open":
                result.add(createEvent(oldResource, newResource, EventType.Ec2_Spot_Instance_Request_Created));
                break;
            case "active":
                result.add(createEvent(oldResource, newResource, EventType.Ec2_Spot_Instance_Request_Active));
                break;
            case "closed":
                result.add(createEvent(oldResource, newResource, EventType.Ec2_Spot_Instance_Request_Closed));
                break;
            case "failed":
                result.add(createEvent(oldResource, newResource, EventType.Ec2_Spot_Instance_Request_Failed));
                break;
            default:
                log.error("not handled state:" + newReq.getState());
                result.add(createEvent(oldResource, newResource, EventType.Unknown));
            }
        }

        if (notEqual(oldReq.getStatus().getCode(), newReq.getStatus().getCode())) {
            switch (newReq.getStatus().getCode()) {
            case "pending-fulfillment":
                result.add(createEvent(oldResource, newResource, EventType.Ec2_Spot_Instance_Request_Pending_Fulfillment));
            case "price-too-low":
                result.add(createEvent(oldResource, newResource, EventType.Ec2_Spot_Instance_Request_Price_Too_Low));
                break;
            case "fulfilled":
                result.add(createEvent(oldResource, newResource, EventType.Ec2_Spot_Instance_Request_Fulfilled));
                break;
            case "instance-terminated-by-price":
                result.add(createEvent(oldResource, newResource, EventType.Ec2_Spot_Instance_Request_Fulfilled));
                break;
            case "instance-terminated-no-capacity":
                result.add(createEvent(oldResource, newResource, EventType.Ec2_Spot_Instance_Request_Instance_Terminated_No_Capacity));
                break;
            default:
                log.error("not handled status code:" + newReq.getStatus().getCode());
                result.add(createEvent(oldResource, newResource, EventType.Unknown));
            }
        }
    }
}
