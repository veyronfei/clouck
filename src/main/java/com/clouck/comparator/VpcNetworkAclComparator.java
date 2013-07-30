package com.clouck.comparator;

import java.util.List;

import org.springframework.stereotype.Component;

import com.clouck.model.Event;
import com.clouck.model.aws.ec2.VpcNetworkAcl;

@Component
public class VpcNetworkAclComparator extends AbstractEc2Comparator<VpcNetworkAcl> {

    @Override
    public Event initialise(VpcNetworkAcl newResource) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Event add(VpcNetworkAcl newResource) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected void update(List<Event> result, VpcNetworkAcl oldResource, VpcNetworkAcl newResource) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public Event delete(VpcNetworkAcl oldResource) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Event firstScan() {
        // TODO Auto-generated method stub
        return null;
    }

}
