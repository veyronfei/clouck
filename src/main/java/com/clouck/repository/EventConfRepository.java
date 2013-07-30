package com.clouck.repository;

import org.springframework.data.repository.CrudRepository;

import com.clouck.model.EventConfig;

public interface EventConfRepository extends CrudRepository<EventConfig, String>, EventConfRepositoryCustom {
}
