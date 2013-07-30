package com.clouck.repository;

import org.springframework.data.repository.CrudRepository;

import com.clouck.model.Event;

public interface EventRepository extends CrudRepository<Event, String>, EventRepositoryCustom {
}
