package com.clouck.comparator;

import java.util.List;

import org.springframework.stereotype.Component;

import com.clouck.model.Event;
import com.clouck.model.aws.ec2.VpcSubnet;

@Component
public class VpcSubnetComparator extends AbstractEc2Comparator<VpcSubnet> {

    @Override
    public Event initialise(VpcSubnet newResource) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Event add(VpcSubnet newResource) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected void update(List<Event> result, VpcSubnet oldResource, VpcSubnet newResource) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public Event delete(VpcSubnet oldResource) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Event firstScan() {
        // TODO Auto-generated method stub
        return null;
    }

}
