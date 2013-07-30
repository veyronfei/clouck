package com.clouck.service;

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

public interface AwsService {

    long findLatestResourceSizes(String accountId, ResourceType resourceType, Region region);

    List<Ec2Version> findEc2VersionsFromIncludeOrderByTimeDetected(String accountId, ResourceType resourceType, Region region, DateTime dateTime);

    Optional<Ec2Version> findEc2Version(String ec2VersionId);

    List<AbstractResource<?>> findResources(Collection<String> ids, ResourceType resourceType);

//    Collection<AbstractResource<?>> findResources(String accountId, ResourceType resourceType, DateTime dateTime);
    
    List<AbstractResource<?>> findResourcesAt(String accountId, ResourceType resourceType, Region region, DateTime dateTime);

    Collection<AbstractResource<?>> findResources(String ec2VersionId);

    List<Ec2Version> findEc2VersionsFromExcludeOrderByTimeDetected(String accountId, ResourceType resourceType, Region region, DateTime dateTime);

    String findUserId(String accessKeyId, String secretAccessKey);

    @SuppressWarnings("rawtypes")
    Optional<AbstractResource> findResource(String resourceId, ResourceType resourceType);

    List<? extends AbstractResource<?>> findResources(ResourceType resourceType, String accountId, String uniqueId);

    Optional<Ec2Version> findLatestEc2VersionUptoInclude(String accountId, Region region, ResourceType resourceType, DateTime dt);

    Optional<Ec2Version> findLatestEc2Version(String accountId, ResourceType resourceType, Region region);

    Optional<Ec2Version> findFirstEc2Version(String accountId, Region region, ResourceType resourceType);

    long countEc2VersionsUpto(String accountId, ResourceType resourceType, Region region, DateTime dt, Boolean isIncludeTime);

    long countEc2Versions(String accountId, ResourceType resourceType, Region region);

    long countEc2VersionMetas(String accountId, ResourceType resourceType, Region region);

    Optional<Ec2VersionMeta> findLatestEc2VersionMeta(String accountId, ResourceType resourceType, Region region);

    List<Ec2Version> findEc2VersionsOrderByTimeDetectedAsc(String accountId, ResourceType ec2Reservation, Region region);

    List<Ec2Version> findEc2VersionsOrderByTimeDetectedDesc(String accountId, ResourceType resourceType, Region region);

    List<Ec2VersionMeta> findEc2VersionMetasOrderByTimeDetectedDesc(String accountId, ResourceType resourceType, Region region, Pageable pageable);

    long countEc2VersionMetas(String accountId, ResourceType rt, Region region, String uniqueId);

    List<Ec2VersionMeta> findEc2VersionMetasOrderByTimeDetectedDesc(String accountId, Region region, ResourceType rt, String uniqueId);

    List<Ec2VersionMeta> findEc2VersionMetasOrderByTimeDetectedDesc(String accountId, Region region, Pageable pageable);

    List<Ec2VersionMeta> findEc2VersionMetasOrderByTimeDetectedDesc(String accountId, ResourceType resourceType, Region region, Pageable pageable, DateTime dt, Boolean isIncludeTime, String uniqueId);

    long countEc2VersionMetas(String accountId, ResourceType resourceType, Region region, DateTime dt, Boolean isIncludeTime, String uniqueId);

    long countEc2VersionMetas(String accountId, ResourceType resourceType);
}
