package com.clouck.validator;

import java.util.Collection;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.Validate;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.clouck.model.Account;
import com.clouck.model.Region;
import com.clouck.model.ResourceType;
import com.clouck.model.aws.AbstractResource;
import com.clouck.model.aws.ec2.Ec2Version;
import com.clouck.service.AwsService;
import com.google.common.base.Optional;

@Component
public class Ec2ResourceValidator {
    @Autowired
    private AwsService awsService;

    public <R extends AbstractResource<?>> void validate(Collection<R> existingResources, Collection<R> newResources) {
        notNullAndSameAccountIdAndRegion(existingResources);
        notNullAndSameAccountIdAndRegion(newResources);
        sameAccountIdAndRegionAcrossTwoLists(existingResources, newResources);
    }

    public <R extends AbstractResource<?>> void notNullAndSameAccountIdAndRegion(Collection<R> resources) {
        Validate.notNull(resources);
        String preAccountId = null;
        Region preRegion = null;
        for (R resource : resources) {
            String accountId = resource.getAccountId();
            Region region = resource.getRegion();
            Validate.notNull(accountId);
            ResourceType rt = ResourceType.find(resource);
            if (rt.isMultiRegion()) {
                Validate.notNull(region);
            }
            if (preAccountId == null) {
                preAccountId = accountId;
            }
            if (preRegion == null) {
                preRegion = region;
            }
            Validate.isTrue(ObjectUtils.equals(accountId, preAccountId));
            Validate.isTrue(ObjectUtils.equals(region, preRegion));
        }
    }

    public <R extends AbstractResource<?>> void sameAccountIdAndRegionAcrossTwoLists(Collection<R> existingResources, Collection<R> newResources) {
        Validate.noNullElements(new Object[]{existingResources, newResources});
        R tmp = null;
        for (R resource : existingResources) {
            tmp = resource;
            break;
        }
        if (tmp == null) {
            return;
        }
        for (R resource : newResources) {
            Validate.isTrue(tmp.getClass().equals(resource.getClass()));
            break;
        }
    }

    public void validate(Ec2Version oldVersion, Ec2Version newVersion) {
        Validate.noNullElements(new Object[]{oldVersion, newVersion});
        Validate.isTrue(oldVersion.getAccountId().equals(newVersion.getAccountId()));
        Validate.isTrue(oldVersion.getRegion().equals(newVersion.getRegion()));
        Validate.isTrue(oldVersion.getResourceType().equals(newVersion.getResourceType()));
    }

    public <R extends AbstractResource<?>> void isSameTime(Collection<R> newResources, DateTime dt) {
        Validate.noNullElements(new Object[]{newResources, dt});
        for (R resource : newResources) {
            Validate.isTrue(dt.toInstant().getMillis() == resource.getTimeDetected().getTime());
        }
    }

    public void isLatestTime(Account account, Region region, ResourceType resourceType, DateTime dt) {
        Validate.noNullElements(new Object[]{account, region, resourceType, dt});
        Optional<Ec2Version> oEc2Version = awsService.findLatestEc2Version(account.getId(), resourceType, region);
        if (oEc2Version.isPresent()) {
            Ec2Version ec2Version = oEc2Version.get();
            if (ec2Version.getTimeDetected().after(dt.toDate())) {
                throw new IllegalArgumentException(String.format("latest ec2version is:%s which is later than datetime:%s",
                        ec2Version.getTimeDetected(), dt.toDate()));
            }
        }
    }
}

