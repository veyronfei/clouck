package com.clouck.comparator;

import java.util.List;

import org.springframework.stereotype.Component;

import com.clouck.model.Event;
import com.clouck.model.aws.iam.IamUser;

@Component
public class IamUserComparator extends AbstractEc2Comparator<IamUser> {

    @Override
    public Event initialise(IamUser newResource) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Event add(IamUser newResource) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected void update(List<Event> result, IamUser oldResource, IamUser newResource) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public Event delete(IamUser oldResource) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Event firstScan() {
        // TODO Auto-generated method stub
        return null;
    }

}
