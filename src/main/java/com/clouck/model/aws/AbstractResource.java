package com.clouck.model.aws;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazonaws.services.ec2.model.Tag;
import com.clouck.exception.ClouckError;
import com.clouck.model.AbstractModel;
import com.clouck.model.Region;
import com.clouck.model.ResourceType;

@SuppressWarnings("serial")
public abstract class AbstractResource<R> extends AbstractModel {
    private static final Logger log = LoggerFactory.getLogger(AbstractResource.class);

    protected R resource;
    private Date timeDetected;
    //if this field is empty, it's for single region resource. e.g. iam
    private Region region;
    private String accountId;

    public String getUniqueId() {
        ResourceType rt = ResourceType.find(this);
        String uniqueIds = "";
        for (String uniqueIdName : rt.getUniqueIdNames()) {
            try {
                uniqueIds += PropertyUtils.getSimpleProperty(this.getResource(), uniqueIdName);
            } catch (Exception e) {
                throw new ClouckError(e);
            }
        }
        return uniqueIds;
    }

    @SuppressWarnings("unchecked")
    public String getTag() {
        try {
            List<Tag> tags = (List<Tag>) PropertyUtils.getSimpleProperty(this.getResource(), "tags");
            for (Tag tag : tags) {
                if (tag.getKey().equals("Name")) {
                    return tag.getValue();
                }
            }
            return null;
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            return null;
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean equals(Object obj) {
        AbstractResource<R> newResource = (AbstractResource<R>)obj;
        if (this == newResource) return true;
        if (newResource == null) return false;
        return new EqualsBuilder().append(region, newResource.getRegion())
                .append(accountId, newResource.getAccountId()).isEquals() && isEqual(newResource);
    }

    protected abstract boolean isEqual(AbstractResource<R> newResource);

    public long getInstant() {
        return timeDetected.getTime();
    }

    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public R getResource() {
        return resource;
    }

    public void setResource(R resource) {
        this.resource = resource;
    }

    public Date getTimeDetected() {
        return timeDetected;
    }

    public void setTimeDetected(Date timeDetected) {
        this.timeDetected = timeDetected;
    }
}
