package com.clouck.comparator;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.amazonaws.services.elasticloadbalancing.model.LoadBalancerDescription;
import com.clouck.model.Event;
import com.clouck.model.EventType;
import com.clouck.model.aws.ec2.Ec2LoadBalancer;
import com.clouck.util.ResourceUtil;

@Component
public class Ec2LoadBalancerComparator extends AbstractEc2Comparator<Ec2LoadBalancer> {

    @Autowired
    private ResourceUtil resourceUtil;

    @Override
    public Event firstScan() {
        return createFirstScanEvent(EventType.Ec2_Load_Balancer_First_Scan);
    }

    @Override
    public Event initialise(Ec2LoadBalancer newResource) {
        return createEvent(null, newResource, EventType.Ec2_Load_Balancer_Found);
    }

    @Override
    public Event add(Ec2LoadBalancer newResource) {
        return createEvent(null, newResource, EventType.Ec2_Load_Balancer_Created);
    }

    @Override
    protected void update(List<Event> result, Ec2LoadBalancer oldResource, Ec2LoadBalancer newResource) {
        LoadBalancerDescription oldLoadBalancer = oldResource.getResource();
        LoadBalancerDescription newLoadBalancer = newResource.getResource();
    }

    @Override
    public Event delete(Ec2LoadBalancer oldResource) {
        return createEvent(oldResource, null, EventType.Ec2_Load_Balancer_Deleted);
    }
}
