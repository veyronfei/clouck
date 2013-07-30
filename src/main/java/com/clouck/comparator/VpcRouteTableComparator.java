package com.clouck.comparator;

import java.util.List;

import org.springframework.stereotype.Component;

import com.clouck.model.Event;
import com.clouck.model.aws.ec2.VpcRouteTable;

@Component
public class VpcRouteTableComparator extends AbstractEc2Comparator<VpcRouteTable> {

    @Override
    public Event initialise(VpcRouteTable newResource) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Event add(VpcRouteTable newResource) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected void update(List<Event> result, VpcRouteTable oldResource, VpcRouteTable newResource) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public Event delete(VpcRouteTable oldResource) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Event firstScan() {
        // TODO Auto-generated method stub
        return null;
    }

}
