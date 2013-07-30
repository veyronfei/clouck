package com.clouck.model.aws.ec2;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

import com.clouck.model.AbstractModel;
import com.clouck.model.Region;
import com.clouck.model.ResourceType;

@Document(collection = "ec2_version")
@TypeAlias(value = "ec2_version")
@SuppressWarnings("serial")
public class Ec2Version extends AbstractModel {
    private String accountId;
    //if there field is empty, it's for single region resource. e.g. iam
    private Region region;
    private ResourceType resourceType;
    private List<String> resourceIds = new ArrayList<>();
    //this time should be different for each record with same accountId, region and resourceType
    //but should be same with resources included in resourceIds
    private Date timeDetected;

    public Ec2Version(String accountId, Region region, Date timeDetected, ResourceType resourceType) {
        this.accountId = accountId;
        this.region = region;
        this.timeDetected = timeDetected;
        this.resourceType = resourceType;
    }

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

    public List<String> getResourceIds() {
        return resourceIds;
    }

    public void setResourceIds(List<String> resourceIds) {
        this.resourceIds = resourceIds;
    }

    public Date getTimeDetected() {
        return timeDetected;
    }

    public void setTimeDetected(Date timeDetected) {
        this.timeDetected = timeDetected;
    }
}
