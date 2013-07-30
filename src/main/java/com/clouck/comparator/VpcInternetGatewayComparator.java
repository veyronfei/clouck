package com.clouck.comparator;

import java.util.List;

import org.springframework.stereotype.Component;

import com.clouck.model.Event;
import com.clouck.model.aws.ec2.VpcInternetGateway;

@Component
public class VpcInternetGatewayComparator extends AbstractEc2Comparator<VpcInternetGateway> {

    @Override
    public Event initialise(VpcInternetGateway newResource) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Event add(VpcInternetGateway newResource) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected void update(List<Event> result, VpcInternetGateway oldResource, VpcInternetGateway newResource) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public Event delete(VpcInternetGateway oldResource) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Event firstScan() {
        // TODO Auto-generated method stub
        return null;
    }

}
