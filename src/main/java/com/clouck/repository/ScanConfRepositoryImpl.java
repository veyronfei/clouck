package com.clouck.repository;

import static org.springframework.data.mongodb.core.query.Criteria.where;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Query;

import com.clouck.model.Region;
import com.clouck.model.ResourceType;
import com.clouck.model.ScanConfig;
import com.google.common.base.Optional;

public class ScanConfRepositoryImpl implements ScanConfRepositoryCustom {

    @Autowired
    private MongoOperations mongoOps;

    @Override
    public Optional<ScanConfig> findScanConf(String accountId, ResourceType resourceType, Region region) {
        ScanConfig result = mongoOps.findOne(
                new Query(where("accountId").is(accountId).
                        and("resourceType").is(resourceType).
                        and("region").is(region)),
                        ScanConfig.class);
        if (result != null) {
            return Optional.of(result);
        } else {
            return Optional.absent();
        }
    }
}
