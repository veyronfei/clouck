package com.clouck.repository;

import static org.springframework.data.mongodb.core.query.Criteria.where;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Query;

import com.clouck.model.Event;
import com.clouck.model.Region;
import com.clouck.model.ResourceType;
import com.google.common.base.Optional;

public class EventRepositoryImpl implements EventRepositoryCustom {

    @Autowired
    private MongoOperations mongoOps;

    @Override
    public Optional<Event> findLatestEvent(String accountId, ResourceType resourceType, Region region) {
        Event result = mongoOps.findOne(
                new Query(where("accountId").is(accountId).
                        and("resourceType").is(resourceType).
                        and("region").is(region)).
                        with(new Sort(Direction.DESC, "timeDetected")),
                        Event.class);
        if (result != null) {
            return Optional.of(result);
        } else {
            return Optional.absent();
        }
    }

//    @Override
//    public Optional<EventConf> find(String accountId, ResourceType resourceType, Region region) {
//        EventConf result = mongoOps.findOne(
//                new Query(where("accountId").is(accountId).
//                        and("resourceType").is(resourceType).
//                        and("region").is(region)),
//                        EventConf.class);
//        if (result != null) {
//            return Optional.of(result);
//        } else {
//            return Optional.absent();
//        }
//    }

    @Override
    public List<Event> findEvents(String accountId, int size) {
        return mongoOps.find(
                new Query(where("accountId").is(accountId)).
                        with(new Sort(Direction.DESC, "timeDetected")).
                        limit(size),
                        Event.class);
    }

    @Override
    public List<Event> findEvents(Set<String> newEc2VersionIds) {
        return mongoOps.find(
                new Query(where("newEc2VersionId").in(newEc2VersionIds)).
                        with(new Sort(Direction.DESC, "timeDetected")),
                        Event.class);
    }

    @Override
    public List<Event> findEventsByResourceId(String resourceId) {
        return mongoOps.find(
                new Query(where("resourceId").is(resourceId)).
                        with(new Sort("timeDetected")),
                        Event.class);
    }
    
    @Override
    public List<Event> findEventsByUniqueId(String uniqueId) {
        return mongoOps.find(
                new Query(where("uniqueId").is(uniqueId)).
                        with(new Sort("timeDetected")),
                        Event.class);
    }
}
