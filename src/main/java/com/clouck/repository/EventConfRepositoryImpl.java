package com.clouck.repository;

import static org.springframework.data.mongodb.core.query.Criteria.where;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Query;

import com.clouck.model.EventConfig;
import com.clouck.model.Region;
import com.clouck.model.ResourceType;
import com.google.common.base.Optional;

public class EventConfRepositoryImpl implements EventConfRepositoryCustom {

    @Autowired
    private MongoOperations mongoOps;

    @Override
    public Optional<EventConfig> findEventConf(String accountId, ResourceType resourceType, Region region) {
        EventConfig result = mongoOps.findOne(
                new Query(where("accountId").is(accountId).
                        and("resourceType").is(resourceType).
                        and("region").is(region)),
                        EventConfig.class);
        if (result != null) {
            return Optional.of(result);
        } else {
            return Optional.absent();
        }
    }
}
