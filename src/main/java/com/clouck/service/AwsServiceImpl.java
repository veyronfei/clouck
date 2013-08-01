package com.clouck.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang3.Validate;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.clouck.model.Region;
import com.clouck.model.ResourceType;
import com.clouck.model.aws.AbstractResource;
import com.clouck.model.aws.ec2.Ec2Version;
import com.clouck.model.aws.ec2.Ec2VersionMeta;
import com.clouck.repository.AwsRepository;
import com.clouck.repository.Ec2VersionRepository;
import com.clouck.wrapper.aws.IamWrapper;
import com.google.common.base.Optional;

@Service
public class AwsServiceImpl implements AwsService {
    private static final Logger log = LoggerFactory.getLogger(AwsServiceImpl.class);

    @Autowired
    private IamWrapper iam;
    @Autowired
    private AwsRepository awsDao;
    @Autowired
    private Ec2VersionRepository ec2VersionDao;

    @Override
    public long findLatestResourceSizes(String accountId, ResourceType resourceType, Region region) {
        Validate.noNullElements(new Object[]{accountId, resourceType, region});

        long total = 0;
        if (region.equals(Region.All)) {
            if (resourceType.isMultiRegion()) {
                for (Region r : Region.findAvailableRegions(resourceType)) {
                    Optional<Ec2Version> oEc2Version = awsDao.findEc2VersionUpto(accountId, resourceType, r, DateTime.now(), true, false);
                    if (oEc2Version.isPresent()) {
                        total += oEc2Version.get().getResourceIds().size();
                    }
                }
            } else {
                Optional<Ec2Version> oEc2Version = awsDao.findEc2VersionUpto(accountId, resourceType, null, DateTime.now(), true, false);
                if (oEc2Version.isPresent()) {
                    total += oEc2Version.get().getResourceIds().size();
                }
            }
        } else {
            Optional<Ec2Version> oEc2Version = awsDao.findEc2VersionUpto(accountId, resourceType, region, DateTime.now(), true, false);
            if (oEc2Version.isPresent()) {
                total = oEc2Version.get().getResourceIds().size();
            }
        }
        return total;
    }

    @Override
    public Collection<AbstractResource<?>> findResources(String ec2VersionId) {
        Collection<AbstractResource<?>> result = new ArrayList<>();

        Optional<Ec2Version> oEc2Version = findEc2Version(ec2VersionId);
        if (oEc2Version.isPresent()) {
            Ec2Version ec2Version = oEc2Version.get();
            Collection<AbstractResource<?>> resources = findResources(ec2Version.getResourceIds(), ec2Version.getResourceType());
            result.addAll(resources);
        }

        return result;
    }


    @Override
    public List<AbstractResource<?>> findResources(Collection<String> ids, ResourceType resourceType) {
        return awsDao.findResources(ids, resourceType);
    }

    @Override
    @SuppressWarnings("rawtypes")
    public Optional<AbstractResource> findResource(String resourceId, ResourceType resourceType) {
        return awsDao.findResource(resourceId, resourceType);
    }

    @Override
    public List<? extends AbstractResource<?>> findResources(ResourceType resourceType, String accountId, String uniqueId) {
        return awsDao.findResource(resourceType, accountId, uniqueId);
    }

    @Override
    public List<AbstractResource<?>> findResourcesAt(String accountId, ResourceType resourceType, Region region, DateTime dateTime) {
        Validate.noNullElements(new Object[]{accountId, resourceType, region, dateTime});

        List<AbstractResource<?>> result = new ArrayList<>();
        if (region.equals(Region.All)) {
            for (Region r : Region.findAvailableRegions(resourceType)) {
                Optional<Ec2Version> oEc2Version = awsDao.findEc2VersionUpto(accountId, resourceType, r, dateTime, true, false);
                if (oEc2Version.isPresent()) {
                    Collection<AbstractResource<?>> resources = awsDao.findResources(oEc2Version.get().getResourceIds(), resourceType);
                    result.addAll(resources);
                }
            }
        } else {
            Optional<Ec2Version> oEc2Version = awsDao.findEc2VersionUpto(accountId, resourceType, region, dateTime, true, false);
            if (oEc2Version.isPresent()) {
                Collection<AbstractResource<?>> resources = awsDao.findResources(oEc2Version.get().getResourceIds(), resourceType);
                result.addAll(resources);
            }
        }

        return result;
    }

    @Override
    public List<Ec2Version> findEc2VersionsFromIncludeOrderByTimeDetected(String accountId, ResourceType resourceType, Region region, DateTime dateTime) {
        Validate.noNullElements(new Object[]{accountId, resourceType, region, dateTime});
        return awsDao.findEc2VersionsFrom(accountId, resourceType, region, dateTime, true, true);
    }

    @Override
    public List<Ec2Version> findEc2VersionsFromExcludeOrderByTimeDetected(String accountId, ResourceType resourceType, Region region, DateTime dateTime) {
        Validate.noNullElements(new Object[]{accountId, resourceType, region, dateTime});
        return awsDao.findEc2VersionsFrom(accountId, resourceType, region, dateTime, false, true);
    }

    public Optional<Ec2Version> findEc2VersionAt(String accountId, ResourceType resourceType, DateTime dateTime) {
        return awsDao.findEc2VersionAt(accountId, resourceType, dateTime);
    }

    @Override
    public List<Ec2Version> findEc2VersionsOrderByTimeDetectedAsc(String accountId, ResourceType resourceType, Region region) {
        Validate.noNullElements(new Object[]{accountId, resourceType, region});
        return awsDao.findEc2VersionsUpto(accountId, resourceType, region, null, null, true);
    }

    @Override
    public List<Ec2Version> findEc2VersionsOrderByTimeDetectedDesc(String accountId, ResourceType resourceType, Region region) {
        Validate.noNullElements(new Object[]{accountId, resourceType, region});
        return awsDao.findEc2VersionsUpto(accountId, resourceType, region, null, null, false);
    }

    @Override
    public long countEc2VersionsUpto(String accountId, ResourceType resourceType, Region region, DateTime dt, Boolean isIncludeTime) {
        Validate.noNullElements(new Object[]{accountId, resourceType, region});
        return awsDao.countEc2VersionsUpto(accountId, resourceType, region, dt, isIncludeTime);
    }

    @Override
    public long countEc2Versions(String accountId, ResourceType resourceType, Region region) {
        return awsDao.countEc2VersionsUpto(accountId, resourceType, region, null, null);
    }

    @Override
    public Optional<Ec2Version> findEc2Version(String ec2VersionId) {
        Ec2Version ec2Version = ec2VersionDao.findOne(ec2VersionId);
        if (ec2Version == null) {
            return Optional.absent();
        } else {
            return Optional.of(ec2Version);
        }
    }

    @Override
    public Optional<Ec2Version> findLatestEc2VersionUptoInclude(String accountId, Region region, ResourceType resourceType, DateTime dt) {
        Validate.noNullElements(new Object[]{accountId, region, resourceType, dt});
        return awsDao.findEc2VersionUpto(accountId, resourceType, region, dt, true, false);
    }

    @Override
    public Optional<Ec2Version> findFirstEc2Version(String accountId, Region region, ResourceType resourceType) {
        Validate.noNullElements(new Object[]{accountId, resourceType, region});
        return awsDao.findEc2VersionUpto(accountId, resourceType, region, null, null, true);
    }

    @Override
    public Optional<Ec2Version> findLatestEc2Version(String accountId, ResourceType resourceType, Region region) {
        Validate.noNullElements(new Object[]{accountId, region, resourceType});
        return awsDao.findEc2VersionUpto(accountId, resourceType, region, null, null, false);
    }

    @Override
    public List<Ec2VersionMeta> findEc2VersionMetasOrderByTimeDetectedDesc(String accountId, ResourceType resourceType, Region region, Pageable pageable) {
        Validate.noNullElements(new Object[]{accountId, resourceType, region});
        Region r = region.equals(Region.All) ? null : region;
        return awsDao.findEc2VersionMetasUpto(accountId, resourceType, r, null, null, false, pageable, null);
    }

    @Override
    public List<Ec2VersionMeta> findEc2VersionMetasOrderByTimeDetectedDesc(String accountId, Region region, Pageable pageable) {
        Validate.noNullElements(new Object[]{accountId, region});
        Region r = region.equals(Region.All) ? null : region;
        return awsDao.findEc2VersionMetasUpto(accountId, null, r, null, null, false, pageable, null);
    }

    @Override
    public List<Ec2VersionMeta> findEc2VersionMetasOrderByTimeDetectedDesc(String accountId, ResourceType resourceType,
            Region region, Pageable pageable, DateTime dt, Boolean isIncludeTime, String uniqueId) {
        Validate.noNullElements(new Object[]{accountId, resourceType, region});
        Region r = region.equals(Region.All) ? null : region;
        return awsDao.findEc2VersionMetasUpto(accountId, resourceType, r, dt, isIncludeTime, false, pageable, uniqueId);
    }

    @Override
    public long countEc2VersionMetas(String accountId, ResourceType resourceType, Region region, DateTime dt, Boolean isIncludeTime, String uniqueId) {
        Validate.noNullElements(new Object[]{accountId, resourceType, region, dt, isIncludeTime});
        Region r = region.equals(Region.All) ? null : region;
        return awsDao.countEc2VersionMetasUpto(accountId, resourceType, r, dt, isIncludeTime, uniqueId);
    }

    @Override
    public long countEc2VersionMetas(String accountId, ResourceType resourceType, Region region) {
        Validate.noNullElements(new Object[]{accountId, resourceType, region});
        Region r = region.equals(Region.All) ? null : region;
        return awsDao.countEc2VersionMetasUpto(accountId, resourceType, r, null, null, null);
    }

    @Override
    public long countEc2VersionMetas(String accountId, ResourceType resourceType) {
        Validate.noNullElements(new Object[]{accountId, resourceType});
        return awsDao.countEc2VersionMetasUpto(accountId, resourceType, null, null, null, null);
    }

    @Override
    public Optional<Ec2VersionMeta> findLatestEc2VersionMeta(String accountId, ResourceType resourceType, Region region) {
        Validate.noNullElements(new Object[]{accountId, resourceType, region});
        Region r = region.equals(Region.All) ? null : region;
        return awsDao.findEc2VersionMetaUpto(accountId, resourceType, r, null, null, false);
    }

//    @Override
//    public Collection<AbstractResource<?>> findResources(String accountId, ResourceType resourceType, DateTime dateTime) {
//        Collection<AbstractResource<?>> result = new ArrayList<>();
//        for (Region region : Region.findAvailableRegions(resourceType)) {
//            result.addAll(findResources(accountId, resourceType, region, dateTime));
//        }
//        return result;
//    }

    @Override
    public String findUserId(String accessKeyId, String secretAccessKey) {
        return iam.findUserId(accessKeyId, secretAccessKey);
    }

    @Override
    public long countEc2VersionMetas(String accountId, ResourceType rt, Region region, String uniqueId) {
        Validate.noNullElements(new Object[]{accountId, rt, region});
        Region r = region.equals(Region.All) ? null : region;
        return awsDao.countEc2VersionMetasUpto(accountId, rt, r, null, null, uniqueId);
    }

    @Override
    public List<Ec2VersionMeta> findEc2VersionMetasOrderByTimeDetectedDesc(String accountId, Region region, ResourceType rt, String uniqueId) {
        Validate.noNullElements(new Object[]{accountId, rt, region, uniqueId});
        Region r = region.equals(Region.All) ? null : region;
        return awsDao.findEc2VersionMetasOrderByTimeDetectedDesc(accountId, rt, r, uniqueId);
    }
}
