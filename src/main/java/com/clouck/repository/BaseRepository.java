package com.clouck.repository;

import java.util.Collection;

public interface BaseRepository {

    void save(Object objectToSave);

    void insertAll(Collection<? extends Object> objectsToSave);

}
