package com.clouck.comparator;

import java.util.List;

import org.springframework.stereotype.Component;

import com.clouck.model.Event;
import com.clouck.model.aws.iam.IamRole;

@Component
public class IamRoleComparator extends AbstractEc2Comparator<IamRole> {

    @Override
    public Event initialise(IamRole newResource) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Event add(IamRole newResource) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected void update(List<Event> result, IamRole oldResource, IamRole newResource) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public Event delete(IamRole oldResource) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Event firstScan() {
        // TODO Auto-generated method stub
        return null;
    }

}
