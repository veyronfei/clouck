package com.clouck.comparator;

import java.util.List;

import org.springframework.stereotype.Component;

import com.clouck.model.Event;
import com.clouck.model.aws.iam.IamGroup;

@Component
public class IamGroupComparator extends AbstractEc2Comparator<IamGroup> {

    @Override
    public Event initialise(IamGroup newResource) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Event add(IamGroup newResource) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected void update(List<Event> result, IamGroup oldResource, IamGroup newResource) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public Event delete(IamGroup oldResource) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Event firstScan() {
        return null;
    }

}
