package com.clouck.rep;

import java.util.ArrayList;
import java.util.List;

import com.clouck.model.Region;

public class Ec2VersionMetaRep {
    private String timeDetected;
    private Region region;
    private long millis;
    private List<EventRep> reps = new ArrayList<>();

    public List<EventRep> getReps() {
        return reps;
    }

    public void setReps(List<EventRep> reps) {
        this.reps = reps;
    }

    public long getMillis() {
        return millis;
    }

    public void setMillis(long millis) {
        this.millis = millis;
    }

    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }

    public String getTimeDetected() {
        return timeDetected;
    }

    public void setTimeDetected(String timeDetected) {
        this.timeDetected = timeDetected;
    }
}
