package com.clouck.repository;

import org.springframework.data.repository.CrudRepository;

import com.clouck.model.ScanConfig;

public interface ScanConfRepository extends CrudRepository<ScanConfig, String>, ScanConfRepositoryCustom {
}
