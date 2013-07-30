package com.clouck.rep;

import java.util.ArrayList;
import java.util.List;

import com.clouck.model.Region;

public class Ec2ResourceRep {
    private String timeDetected;
    private Region region;
    private String resourceId;
    private List<EventRep> reps = new ArrayList<>();

    public String getTimeDetected() {
        return timeDetected;
    }

    public void setTimeDetected(String timeDetected) {
        this.timeDetected = timeDetected;
    }
    
    public List<EventRep> getReps() {
        return reps;
    }

    public void setReps(List<EventRep> reps) {
        this.reps = reps;
    }

    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }
}
