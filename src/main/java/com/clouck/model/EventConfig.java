package com.clouck.model;

import java.util.Date;

import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

@TypeAlias(value = "event_config")
@Document(collection = "event_config")
@SuppressWarnings("serial")
public class EventConfig extends AbstractModel {
    private String accountId;
    private ResourceType resourceType;
    private Region region;
    private Date lastProcessTime;

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }

    public ResourceType getResourceType() {
        return resourceType;
    }

    public void setResourceType(ResourceType resourceType) {
        this.resourceType = resourceType;
    }

    public Date getLastProcessTime() {
        return lastProcessTime;
    }

    public void setLastProcessTime(Date lastProcessTime) {
        this.lastProcessTime = lastProcessTime;
    }
}
