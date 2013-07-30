package com.clouck.repository;

import com.clouck.model.EventConfig;
import com.clouck.model.Region;
import com.clouck.model.ResourceType;
import com.google.common.base.Optional;

public interface EventConfRepositoryCustom {

    Optional<EventConfig> findEventConf(String accountId, ResourceType resourceType, Region region);
}
