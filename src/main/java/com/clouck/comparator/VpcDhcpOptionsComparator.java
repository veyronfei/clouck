package com.clouck.comparator;

import java.util.List;

import org.springframework.stereotype.Component;

import com.clouck.model.Event;
import com.clouck.model.aws.ec2.VpcDhcpOptions;

@Component
public class VpcDhcpOptionsComparator extends AbstractEc2Comparator<VpcDhcpOptions> {

    @Override
    public Event initialise(VpcDhcpOptions newResource) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Event add(VpcDhcpOptions newResource) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected void update(List<Event> result, VpcDhcpOptions oldResource, VpcDhcpOptions newResource) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public Event delete(VpcDhcpOptions oldResource) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Event firstScan() {
        // TODO Auto-generated method stub
        return null;
    }

}
