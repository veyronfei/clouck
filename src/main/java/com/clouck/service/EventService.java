package com.clouck.service;

import java.util.List;
import java.util.Set;

import com.clouck.model.Account;
import com.clouck.model.Event;
import com.clouck.model.aws.AbstractResource;
import com.clouck.model.aws.ec2.Ec2Version;
import com.clouck.model.aws.ec2.Ec2VersionMeta;

public interface EventService {

    List<Event> findEvents(Set<String> newEc2VersionIds);

    List<Event> findEventsByResourceId(String resourceId);

    List<Event> findEventsByUniqueId(String uniqueId);

    void generateEvents(Account account);

    /**
     * generate events for list of ec2versions starting from index 2
     * @param ec2Versions can't be empty
     * @return result size should always be size of input - 1
     */
    List<Ec2VersionMeta> generateEvents(List<Ec2Version> ec2Versions);
//
    Ec2VersionMeta generateEvents(Ec2Version oldVersion, Ec2Version newVersion);

    List<Event> update(AbstractResource<?> oldResource, AbstractResource<?> newResource);

    Event add(AbstractResource<?> newResource);

    Event initialise(AbstractResource<?> newResource);

    Event delete(AbstractResource<?> oldResource);

    List<Event> generateEvents(List<AbstractResource<?>> oldResources, List<AbstractResource<?>> newResources);

//    List<Event> findEvents(String accountId, int size);

    /**
     * generate initialization event for the first ec2 version
     * @param firstEc2Version
     * @return
     */
    Ec2VersionMeta generateEvents(Ec2Version firstEc2Version);







//    List<AbstractEvent> findAscTop10EventsSince(Account account, Long eventId);
//
//    List<AbstractEvent> findEvents(Account account);
}
