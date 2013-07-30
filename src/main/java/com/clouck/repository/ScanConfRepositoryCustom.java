package com.clouck.repository;

import com.clouck.model.Region;
import com.clouck.model.ResourceType;
import com.clouck.model.ScanConfig;
import com.google.common.base.Optional;

public interface ScanConfRepositoryCustom {

    Optional<ScanConfig> findScanConf(String accountId, ResourceType resourceType, Region region);
}
