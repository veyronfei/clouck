package com.clouck.repository;

import static org.springframework.data.mongodb.core.query.Criteria.where;

import java.util.Collection;
import java.util.List;

import org.apache.commons.lang.Validate;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.clouck.exception.ClouckUnexpectedConditionException;
import com.clouck.model.Region;
import com.clouck.model.ResourceType;
import com.clouck.model.aws.AbstractResource;
import com.clouck.model.aws.ec2.Ec2Version;
import com.clouck.model.aws.ec2.Ec2VersionMeta;
import com.google.common.base.Optional;
import com.google.common.collect.Lists;

@Repository
public class AwsRepositoryImpl implements AwsRepository {
    private static final Logger log = LoggerFactory.getLogger(AwsRepositoryImpl.class);

    @Autowired
    private MongoOperations mongoOps;

    private Query createQuery(String accountId, ResourceType resourceType, Region region, DateTime dt,
            Boolean isIncludeTime, Boolean isAsc, boolean isUptoOrFrom, Pageable pageable) {
        Query query = new Query(createCriteria(accountId, resourceType, region, dt, isIncludeTime, isUptoOrFrom));

        if (isAsc != null) {
            if (isAsc) {
                query.with(new Sort(Direction.ASC, "timeDetected"));
            } else {
                query.with(new Sort(Direction.DESC, "timeDetected"));
            }
        }

        if (pageable != null) {
            query.with(pageable);
        }

        return query;
    }

    private Query createQuery(Criteria criteria, Boolean isAsc, Pageable pageable) {
        Query query = new Query(criteria);

        if (isAsc != null) {
            if (isAsc) {
                query.with(new Sort(Direction.ASC, "timeDetected"));
            } else {
                query.with(new Sort(Direction.DESC, "timeDetected"));
            }
        }

        if (pageable != null) {
            query.with(pageable);
        }

        return query;
    }

    private Criteria createCriteria(String accountId, ResourceType resourceType, Region region, DateTime dt,
            Boolean isIncludeTime, boolean isUptoOrFrom) {
        Validate.notNull(accountId);
        Validate.isTrue((dt != null && isIncludeTime != null) || (dt == null && isIncludeTime == null));
        Validate.isTrue(region == null ? true : !region.equals(Region.All));
        Criteria criteria = where("accountId").is(accountId);

        if (resourceType != null) {
            criteria = criteria.and("resourceType").is(resourceType);
        }

        if (region != null) {
            criteria = criteria.and("region").is(region);
        }

        if (dt != null) {
            if (isIncludeTime) {
                if (isUptoOrFrom) {
                    criteria = criteria.and("timeDetected").lte(dt.toDate());
                } else {
                    criteria = criteria.and("timeDetected").gte(dt.toDate());
                }
            } else {
                if (isUptoOrFrom) {
                    criteria = criteria.and("timeDetected").lt(dt.toDate());
                } else {
                    criteria = criteria.and("timeDetected").gt(dt.toDate());
                }
            }
        }

        return criteria;
    }

    @Override
    public Optional<Ec2Version> findEc2VersionUpto(String accountId, ResourceType resourceType, Region region, DateTime dt, Boolean isIncludeTime, boolean isAsc) {
        Query query = createQuery(accountId, resourceType, region, dt, isIncludeTime, isAsc, true, null);

        Ec2Version result = mongoOps.findOne(query, Ec2Version.class);

        if (result != null) {
            return Optional.of(result);
        } else {
            return Optional.absent();
        }
    }

    @Override
    public List<Ec2Version> findEc2VersionsUpto(String accountId, ResourceType resourceType, Region region, DateTime dt, Boolean isIncludeTime, boolean isAsc) {
        Query query = createQuery(accountId, resourceType, region, dt, isIncludeTime, isAsc, true, null);

        return mongoOps.find(query, Ec2Version.class);
    }

    @Override
    public long countEc2VersionsUpto(String accountId, ResourceType resourceType, Region region, DateTime dt, Boolean isIncludeTime) {
        Query query = createQuery(accountId, resourceType, region, dt, isIncludeTime, null, true, null);

        return mongoOps.count(query, Ec2Version.class);
    }

    @Override
    public List<Ec2Version> findEc2VersionsFrom(String accountId, ResourceType resourceType, Region region, DateTime dt, Boolean isIncludeTime, Boolean isAsc) {
        Query query = createQuery(accountId, resourceType, region, dt, isIncludeTime, isAsc, false, null);

        return mongoOps.find(query, Ec2Version.class);
    }

    @Override
    public Optional<Ec2Version> findEc2VersionAt(String accountId, ResourceType resourceType, DateTime dateTime) {
        Ec2Version result = mongoOps.findOne(
                new Query(where("accountId").is(accountId).
                        and("resourceType").is(resourceType).
                        and("timeDetected").is(dateTime.toDate())),
                        Ec2Version.class);
        if (result != null) {
            return Optional.of(result);
        } else {
            return Optional.absent();
        }
    }

    @Override
    public long countEc2VersionMetasUpto(String accountId, ResourceType resourceType, Region region, DateTime dt, Boolean isIncludeTime, String uniqueId) {
        Criteria criteria = createCriteria(accountId, resourceType, region, dt, isIncludeTime, true);
        if (uniqueId != null) {
            criteria = criteria.and("events.uniqueId").is(uniqueId);
        }

        Query query = createQuery(criteria, null, null);

        return mongoOps.count(query, Ec2VersionMeta.class);
    }
    
    @Override
    public Optional<Ec2VersionMeta> findEc2VersionMetaUpto(String accountId, ResourceType resourceType, Region region, DateTime dt, Boolean isIncludeTime, boolean isAsc) {
        Query query = createQuery(accountId, resourceType, region, dt, isIncludeTime, isAsc, true, null);

        Ec2VersionMeta result = mongoOps.findOne(query, Ec2VersionMeta.class);

        if (result != null) {
            return Optional.of(result);
        } else {
            return Optional.absent();
        }
    }

    @Override
    public List<Ec2VersionMeta> findEc2VersionMetasUpto(String accountId, ResourceType resourceType, Region region,
            DateTime dt, Boolean isIncludeTime, boolean isAsc, Pageable pageable, String uniqueId) {
        Criteria criteria = createCriteria(accountId, resourceType, region, dt, isIncludeTime, true);
        if (uniqueId != null) {
            criteria = criteria.and("events.uniqueId").is(uniqueId);
        }

        Query query = createQuery(criteria, isAsc, pageable);

        return mongoOps.find(query, Ec2VersionMeta.class);
    }

    @Override
    public List<Ec2VersionMeta> findEc2VersionMetasOrderByTimeDetectedDesc(String accountId, ResourceType rt, Region region, String uniqueId) {
        Criteria criteria = where("accountId").is(accountId).and("resourceType").is(rt).and("events.uniqueId").is(uniqueId);

        if (region != null) {
            criteria = criteria.and("region").is(region);
        }

        Query query = new Query(criteria).with(new Sort(Direction.DESC, "timeDetected"));

        return mongoOps.find(query, Ec2VersionMeta.class);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<AbstractResource<?>> findResources(Collection<String> ids, ResourceType resourceType) {
        return (List<AbstractResource<?>>) mongoOps.find(new Query(where("id").in(ids)), resourceType.getResourceClass());
    }

    @Override
    @SuppressWarnings("rawtypes")
    public Optional<AbstractResource> findResource(String resourceId, ResourceType resourceType) {
        AbstractResource result = mongoOps.findOne(
                new Query(where("id").is(resourceId)),
                        resourceType.getResourceClass());
        if (result != null) {
            return Optional.of(result);
        } else {
            return Optional.absent();
        }
    }

    @Override
    public List<? extends AbstractResource<?>> findResource(ResourceType resourceType, String accountId, String uniqueId) {
        return findResource(resourceType, accountId, uniqueId, null);
    }

    @Override
    public List<? extends AbstractResource<?>> findResource(ResourceType resourceType, String accountId, String uniqueId, String secondUniqueId) {
        List<String> uniqueIdNames = Lists.newArrayList(resourceType.getUniqueIdNames());

        switch (uniqueIdNames.size()) {
        case 1:
            return mongoOps.find(
                    new Query(where("resource." + uniqueIdNames.get(0)).is(uniqueId).
                            and("accountId").is(accountId)).
                            with(new Sort(Direction.DESC, "timeDetected")),
                            resourceType.getResourceClass());
        case 2:
            return mongoOps.find(
                    new Query(where("resource." + uniqueIdNames.get(0)).is(uniqueId).
                            and("resource." + uniqueIdNames.get(1)).is(secondUniqueId).
                            and("accountId").is(accountId)).
                            with(new Sort(Direction.DESC, "timeDetected")),
                            resourceType.getResourceClass());
        default:
            throw new ClouckUnexpectedConditionException("up to now, should only support up to two combinations");
        }
    }
}
