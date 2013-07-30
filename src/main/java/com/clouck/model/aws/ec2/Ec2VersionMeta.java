package com.clouck.model.aws.ec2;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

import com.clouck.model.AbstractModel;
import com.clouck.model.Event;
import com.clouck.model.Region;
import com.clouck.model.ResourceType;

@Document(collection = "ec2_version_meta")
@TypeAlias(value = "ec2_version_meta")
@SuppressWarnings("serial")
public class Ec2VersionMeta extends AbstractModel {

    private String accountId;
    private Region region;
    private ResourceType resourceType;
    private List<Event> events = new ArrayList<>();
    private String oldEc2VersionId;
    private String newEc2VersionId;
    // this should be equal to newEc2VersionId timeDetected
    private Date timeDetected;

    public Ec2VersionMeta(String accountId, ResourceType resourceType, Region region,
            String oldEc2VersionId, String newEc2VersionId, Date timeDetected) {
        this.accountId = accountId;
        this.resourceType = resourceType;
        this.region = region;
        this.oldEc2VersionId = oldEc2VersionId;
        this.newEc2VersionId = newEc2VersionId;
        this.timeDetected = timeDetected;
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

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }

    public String getOldEc2VersionId() {
        return oldEc2VersionId;
    }

    public void setOldEc2VersionId(String oldEc2VersionId) {
        this.oldEc2VersionId = oldEc2VersionId;
    }

    public String getNewEc2VersionId() {
        return newEc2VersionId;
    }

    public void setNewEc2VersionId(String newEc2VersionId) {
        this.newEc2VersionId = newEc2VersionId;
    }

    public Date getTimeDetected() {
        return timeDetected;
    }

    public void setTimeDetected(Date timeDetected) {
        this.timeDetected = timeDetected;
    }
}
