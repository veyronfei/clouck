package com.clouck.repository;

import java.util.Collection;
import java.util.List;

import org.joda.time.DateTime;
import org.springframework.data.domain.Pageable;

import com.clouck.model.Region;
import com.clouck.model.ResourceType;
import com.clouck.model.aws.AbstractResource;
import com.clouck.model.aws.ec2.Ec2Version;
import com.clouck.model.aws.ec2.Ec2VersionMeta;
import com.google.common.base.Optional;

public interface AwsRepository {

    List<AbstractResource<?>> findResources(Collection<String> ids, ResourceType resourceType);

    long countEc2VersionsUpto(String accountId, ResourceType resourceType, Region region, DateTime dt, Boolean isIncludeTime);

    long countEc2VersionMetasUpto(String accountId, ResourceType resourceType, Region region, DateTime dt, Boolean isIncludeTime, String uniqueId);

    @SuppressWarnings("rawtypes")
    Optional<AbstractResource> findResource(String resourceId, ResourceType resourceType);

    List<? extends AbstractResource<?>> findResource(ResourceType resourceType, String accountId, String uniqueId);

    List<? extends AbstractResource<?>> findResource(ResourceType resourceType, String accountId, String uniqueId, String secondUniqueId);

    Optional<Ec2Version> findEc2VersionAt(String accountId, ResourceType resourceType, DateTime dateTime);

    List<Ec2Version> findEc2VersionsFrom(String accountId, ResourceType resourceType, Region region, DateTime dt, Boolean isIncludeTime, Boolean isAsc);

    Optional<Ec2Version> findEc2VersionUpto(String accountId, ResourceType resourceType, Region region, DateTime dt, Boolean isIncludeTime, boolean isAsc);

    Optional<Ec2VersionMeta> findEc2VersionMetaUpto(String accountId, ResourceType resourceType, Region region, DateTime dt, Boolean isIncludeTime, boolean isAsc);

    List<Ec2Version> findEc2VersionsUpto(String accountId, ResourceType resourceType, Region region, DateTime dt, Boolean isIncludeTime, boolean isAsc);

    List<Ec2VersionMeta> findEc2VersionMetasUpto(String accountId, ResourceType resourceType, Region region, DateTime dt, Boolean isIncludeTime, boolean isAsc, Pageable pageable, String uniqueId);

    List<Ec2VersionMeta> findEc2VersionMetasOrderByTimeDetectedDesc(String accountId, ResourceType rt, Region region, String uniqueId);
}
