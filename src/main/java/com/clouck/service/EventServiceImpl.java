package com.clouck.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.Validate;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.clouck.application.SystemCache;
import com.clouck.comparator.Ec2Comparator;
import com.clouck.exception.CloudVersionEc2CompparatorNotFoundException;
import com.clouck.model.Account;
import com.clouck.model.Event;
import com.clouck.model.EventType;
import com.clouck.model.Region;
import com.clouck.model.ResourceType;
import com.clouck.model.aws.AbstractResource;
import com.clouck.model.aws.ec2.Ec2Version;
import com.clouck.model.aws.ec2.Ec2VersionMeta;
import com.clouck.repository.EventRepository;
import com.clouck.util.ResourceUtil;
import com.clouck.validator.Ec2ResourceValidator;
import com.clouck.wrapper.rabbit.MQWrapper;
import com.google.common.base.Optional;

@Service
public class EventServiceImpl implements EventService {
    private static final Logger log = LoggerFactory.getLogger(EventServiceImpl.class);

    @Autowired
    private AccountService accountService;
    @Autowired
    private AwsService awsService;
    @Autowired
    private EventRepository eventDao;
    @Autowired
    private BaseService baseService;
    @Autowired
    private Ec2ResourceValidator validator;
    @Autowired
    private SystemCache systemCache;
    @Autowired
    private ConfService confService;
    @Autowired
    private MQWrapper mQWrapper;
    @Autowired
    private ResourceUtil resourceUtil;

    @Override
    public void generateEvents(Account account) {
        Validate.notNull(account);
        log.debug("============generate events for account:({})============", account.getId() + "==>" + account.getName());
        for (ResourceType resourceType : ResourceType.findViewResourceTypes()) {
            if (resourceType.isMultiRegion()) {
                for (Region region : Region.findAvailableRegions(resourceType)) {
                    mQWrapper.sendGenerateEventMessage(account.getId(), resourceType, region);
                }
            } else {
                mQWrapper.sendGenerateEventMessage(account.getId(), resourceType);
            }
        }
        log.debug("============ end generate events for account:({})============", account.getId() + "==>" + account.getName());
    }

    @Override
    public List<Ec2VersionMeta> generateEvents(List<Ec2Version> ec2Versions) {
        Validate.notNull(ec2Versions);
        Validate.isTrue(ec2Versions.size() > 0);

        List<Ec2VersionMeta> result = new ArrayList<>();
        for (int i = 0; i < ec2Versions.size() - 1; i++) {
            result.add(generateEvents(ec2Versions.get(i), ec2Versions.get(i + 1)));
        }
        return result;
    }

    @Override
    public Ec2VersionMeta generateEvents(Ec2Version oldVersion, Ec2Version newVersion) {
        validator.validate(oldVersion, newVersion);

        Ec2VersionMeta result = new Ec2VersionMeta(oldVersion.getAccountId(), oldVersion.getResourceType(),
                oldVersion.getRegion(), oldVersion.getId(), newVersion.getId(), newVersion.getTimeDetected());

        List<AbstractResource<?>> oldResources = awsService.findResources(oldVersion.getResourceIds(), oldVersion.getResourceType());
        List<AbstractResource<?>> newResources = awsService.findResources(newVersion.getResourceIds(), newVersion.getResourceType());
        
        List<Event> events = generateEvents(oldResources, newResources);
        result.setEvents(events);
        return result;
    }

    @Override
    public Ec2VersionMeta generateEvents(Ec2Version firstEc2Version) {
        Validate.notNull(firstEc2Version);
        String accountId = firstEc2Version.getAccountId();
        ResourceType resourceType = firstEc2Version.getResourceType();
        Region region = firstEc2Version.getRegion();
        Date dt = firstEc2Version.getTimeDetected();
        Validate.noNullElements(new Object[]{accountId, resourceType, region, dt});
        Validate.isTrue(awsService.countEc2VersionsUpto(accountId, resourceType, region, new DateTime(dt), true) == 1);

        List<Event> events = new ArrayList<>();
        events.add(firstScan(resourceType));
        
        List<AbstractResource<?>> newResources = awsService.findResources(firstEc2Version.getResourceIds(), resourceType);
        for (AbstractResource<?> newResource : newResources) {
            events.add(initialise(newResource));
        }

        Ec2VersionMeta result = new Ec2VersionMeta(accountId, resourceType, region, null, firstEc2Version.getId(), dt);
        result.setEvents(events);

        return result;
    }

    @Override
    public List<Event> generateEvents(List<AbstractResource<?>> oldResources, List<AbstractResource<?>> newResources) {
        validator.validate(oldResources, newResources);
        List<Event> result = new ArrayList<>();

        Map<String, AbstractResource<?>> oldKeyMap = resourceUtil.generateKeyMap(oldResources);
        Map<String, AbstractResource<?>> newKeyMap = resourceUtil.generateKeyMap(newResources);

        for (String uniqueId : newKeyMap.keySet()) {
            AbstractResource<?> newResource = newKeyMap.get(uniqueId);
            AbstractResource<?> oldResource = oldKeyMap.get(uniqueId);

            if (oldResource != null) {
                //if their are same, don't need to generate update events.
                //this can prevent generate an unknown event
                if (!oldResource.equals(newResource)) {
                    log.debug("unique id {} found, generate update events", uniqueId);
                    result.addAll(update(oldResource, newResource));
                } else {
                    log.debug("oldResource {} and newResource {} are equal", oldResource.getId(), newResource.getId());
                }
            } else {
                log.debug("unique id {} not found, generate add events", uniqueId);
                result.add(add(newResource));
            }
            oldKeyMap.remove(uniqueId);
        }

        for (AbstractResource<?> deletedResource : oldKeyMap.values()) {
            log.debug("unique id {} not exist, generate delete events", deletedResource.getUniqueId());
            result.add(delete(deletedResource));
        }

        Validate.isTrue(result.size() > 0);
        return result;
    }

    @Override
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public Event initialise(AbstractResource<?> newResource) {
        Ec2Comparator comparator = systemCache.findComparator(newResource.getClass());
        return comparator.initialise(newResource);
    }

    @SuppressWarnings("rawtypes")
    public Event firstScan(ResourceType resourceType) {
        Ec2Comparator comparator = systemCache.findComparator(resourceType.getResourceClass());
        return comparator.firstScan();
    }

    @Override
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public Event add(AbstractResource<?> newResource) {
        Ec2Comparator comparator = systemCache.findComparator(newResource.getClass());
        return comparator.add(newResource);
    }

    @Override
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public Event delete(AbstractResource<?> oldResource) {
        Ec2Comparator comparator = systemCache.findComparator(oldResource.getClass());
        return comparator.delete(oldResource);
    }

    @Override
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public List<Event> update(AbstractResource<?> oldResource, AbstractResource<?> newResource) {
        Validate.isTrue(oldResource.getClass().equals(newResource.getClass()));
        Ec2Comparator comparator = systemCache.findComparator(newResource.getClass());
        return comparator.update(oldResource, newResource);
    }
//
////    private static Ordering<AbstractEvent> ordering = new Ordering<AbstractEvent>() {
////        public int compare(AbstractEvent left, AbstractEvent right) {
////            return Longs.compare(left.getId(), right.getId());
////        }
////    };
////
////    @Override
////    public List<AbstractEvent> findAscTop10EventsSince(Account account, Long eventId) {
////
////        List<AbstractEvent> events = eventDao.findDescTop10EventsSince(account, eventId);
////        Collections.sort(events, ordering);
////        return events;
////    }
//
//    @Override
//    public List<Event> findEvents(String accountId, int size) {
//        return eventDao.findEvents(accountId, size);
//    }

    @Override
    public List<Event> findEvents(Set<String> newEc2VersionIds) {
        return eventDao.findEvents(newEc2VersionIds);
    }

    @Override
    public List<Event> findEventsByResourceId(String resourceId) {
        return eventDao.findEventsByResourceId(resourceId);
    }

    @Override
    public List<Event> findEventsByUniqueId(String uniqueId) {
        return eventDao.findEventsByUniqueId(uniqueId);
    }
}
