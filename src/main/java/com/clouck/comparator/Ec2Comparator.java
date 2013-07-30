package com.clouck.comparator;

import java.util.List;

import com.clouck.model.Event;
import com.clouck.model.aws.AbstractResource;

public interface Ec2Comparator<R extends AbstractResource<?>> {
    Event firstScan();
    Event initialise(R newResource);
    Event add(R newResource);
    List<Event> update(R oldResource, R newResource);
    Event delete(R oldResource);
    Class<R> getType();
}
