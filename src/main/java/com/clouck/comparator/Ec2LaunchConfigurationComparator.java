package com.clouck.comparator;

import java.util.List;

import org.springframework.stereotype.Component;

import com.clouck.model.Event;
import com.clouck.model.aws.ec2.Ec2LaunchConfiguration;

@Component
public class Ec2LaunchConfigurationComparator extends AbstractEc2Comparator<Ec2LaunchConfiguration> {

    @Override
    public Event initialise(Ec2LaunchConfiguration newResource) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Event add(Ec2LaunchConfiguration newResource) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected void update(List<Event> result, Ec2LaunchConfiguration oldResource, Ec2LaunchConfiguration newResource) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public Event delete(Ec2LaunchConfiguration oldResource) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Event firstScan() {
        // TODO Auto-generated method stub
        return null;
    }

}
