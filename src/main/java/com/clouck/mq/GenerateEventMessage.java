package com.clouck.mq;

import com.clouck.model.Region;
import com.clouck.model.ResourceType;

public class GenerateEventMessage {
    private String accountId;
    private ResourceType resourceType;
    private Region region;

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public ResourceType getResourceType() {
        return resourceType;
    }

    public void setResourceType(ResourceType resourceType) {
        this.resourceType = resourceType;
    }

    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }
}
