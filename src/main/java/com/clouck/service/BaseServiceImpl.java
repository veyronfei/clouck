package com.clouck.service;

import java.util.Collection;

import org.apache.commons.lang.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.clouck.repository.BaseRepository;

@Service
public class BaseServiceImpl implements BaseService {

    @Autowired
    private BaseRepository baseRepository;

    @Override
    public void insertAll(Collection<? extends Object> objectsToSave) {
        Validate.notNull(objectsToSave);
        baseRepository.insertAll(objectsToSave);
    }

    @Override
    public void save(Object objectToSave) {
        Validate.notNull(objectToSave);
        baseRepository.save(objectToSave);
    }
}