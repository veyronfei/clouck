package com.clouck.repository;

import java.util.List;
import java.util.Set;

import com.clouck.model.Event;
import com.clouck.model.Region;
import com.clouck.model.ResourceType;
import com.google.common.base.Optional;

public interface EventRepositoryCustom {

    Optional<Event> findLatestEvent(String accountId, ResourceType resourceType, Region region);

    List<Event> findEvents(String accountId, int size);
    
//    Optional<EventConf> find(String accountId, ResourceType resourceType, Region region);

    List<Event> findEvents(Set<String> newEc2VersionIds);

    List<Event> findEventsByResourceId(String resourceId);

    List<Event> findEventsByUniqueId(String uniqueId);
}
