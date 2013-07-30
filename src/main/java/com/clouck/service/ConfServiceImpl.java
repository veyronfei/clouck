package com.clouck.service;

import org.apache.commons.lang3.Validate;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.clouck.model.EventConfig;
import com.clouck.model.Region;
import com.clouck.model.ResourceType;
import com.clouck.model.ScanConfig;
import com.clouck.repository.EventConfRepository;
import com.clouck.repository.ScanConfRepository;
import com.google.common.base.Optional;

@Service
public class ConfServiceImpl implements ConfService {
    private static final Logger log = LoggerFactory.getLogger(ConfServiceImpl.class);

    @Autowired
    private ScanConfRepository scanConfRepo;
    @Autowired
    private EventConfRepository eventConfRepo;

    @Override
    public Optional<ScanConfig> findScanConf(String accountId, ResourceType resourceType, Region region) {
        Validate.noNullElements(new Object[]{accountId, resourceType, region});
        return scanConfRepo.findScanConf(accountId, resourceType, region);
    }

    @Override
    public Optional<EventConfig> findEventConf(String accountId, ResourceType resourceType, Region region) {
        Validate.noNullElements(new Object[]{accountId, resourceType, region});
        return eventConfRepo.findEventConf(accountId, resourceType, region);
    }

    @Override
    public void createNewScanConf(String accountId, ResourceType resourceType, Region region) {
        Validate.noNullElements(new Object[]{accountId, resourceType, region});
        ScanConfig sc = new ScanConfig();
        sc.setAccountId(accountId);
        sc.setRegion(region);
        sc.setResourceType(resourceType);
        sc.setLastScanTime(DateTime.now().toDate());
        scanConfRepo.save(sc);
    }

    @Override
    public void createNewEventConf(String accountId, ResourceType resourceType, Region region) {
        Validate.noNullElements(new Object[]{accountId, resourceType, region});
        EventConfig ec = new EventConfig();
        ec.setAccountId(accountId);
        ec.setRegion(region);
        ec.setResourceType(resourceType);
        ec.setLastProcessTime(DateTime.now().toDate());
        eventConfRepo.save(ec);
    }

    @Override
    public ScanConfig save(ScanConfig scanConf) {
        Validate.notNull(scanConf);
        return scanConfRepo.save(scanConf);
    }

    @Override
    public EventConfig save(EventConfig eventConf) {
        Validate.notNull(eventConf);
        return eventConfRepo.save(eventConf);
    }
}
