package com.clouck.rep;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.clouck.model.Event;

public class Ec2VersionRep {
    private Date timeDetected;
    private Set<Event> events = new HashSet<>();
    private String regionEndpoint;

    public Date getTimeDetected() {
        return timeDetected;
    }

    public void setTimeDetected(Date timeDetected) {
        this.timeDetected = timeDetected;
    }

    public Set<Event> getEvents() {
        return events;
    }

    public void setEvents(Set<Event> events) {
        this.events = events;
    }

    public String getRegionEndpoint() {
        return regionEndpoint;
    }

    public void setRegionEndpoint(String regionEndpoint) {
        this.regionEndpoint = regionEndpoint;
    }
}
