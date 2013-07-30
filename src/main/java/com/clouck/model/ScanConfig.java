package com.clouck.model;

import java.util.Date;

import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

@TypeAlias(value = "scan_config")
@Document(collection = "scan_config")
@SuppressWarnings("serial")
public class ScanConfig extends AbstractModel {
    private String accountId;
    private ResourceType resourceType;
    private Region region;
    private Date lastScanTime;

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

    public Date getLastScanTime() {
        return lastScanTime;
    }

    public void setLastScanTime(Date lastScanTime) {
        this.lastScanTime = lastScanTime;
    }
}
