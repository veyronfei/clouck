package com.clouck.repository;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Repository;


@Repository
public class BaseRepositoryImpl implements BaseRepository {
    private static final Logger log = LoggerFactory.getLogger(AwsRepositoryImpl.class);
    @Autowired
    private MongoOperations mongoOps;

    @Override
    public void save(Object objectToSave) {
        mongoOps.save(objectToSave);
    }

    @Override
    public void insertAll(Collection<? extends Object> objectsToSave) {
        mongoOps.insertAll(objectsToSave);
    }
}
