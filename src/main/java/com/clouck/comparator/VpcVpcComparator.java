package com.clouck.comparator;

import java.util.List;

import org.springframework.stereotype.Component;

import com.clouck.model.Event;
import com.clouck.model.aws.ec2.VpcVpc;

@Component
public class VpcVpcComparator extends AbstractEc2Comparator<VpcVpc> {

    @Override
    public Event initialise(VpcVpc newResource) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Event add(VpcVpc newResource) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected void update(List<Event> result, VpcVpc oldResource, VpcVpc newResource) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public Event delete(VpcVpc oldResource) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Event firstScan() {
        // TODO Auto-generated method stub
        return null;
    }

}
