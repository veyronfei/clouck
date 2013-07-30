package com.clouck.service;

import com.clouck.model.EventConfig;
import com.clouck.model.Region;
import com.clouck.model.ResourceType;
import com.clouck.model.ScanConfig;
import com.google.common.base.Optional;

public interface ConfService {

    Optional<ScanConfig> findScanConf(String accountId, ResourceType resourceType, Region region);

    Optional<EventConfig> findEventConf(String accountId, ResourceType resourceType, Region region);

    void createNewScanConf(String accountId, ResourceType resourceType, Region region);

    void createNewEventConf(String accountId, ResourceType resourceType, Region region);

    ScanConfig save(ScanConfig scanConf);

    EventConfig save(EventConfig eventConf);
}
