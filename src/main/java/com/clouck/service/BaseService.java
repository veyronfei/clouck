package com.clouck.service;

import java.util.Collection;

public interface BaseService {

    void insertAll(Collection<? extends Object> objectsToSave);

    void save(Object objectToSave);
}
