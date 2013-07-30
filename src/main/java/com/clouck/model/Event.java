package com.clouck.model;

public class Event {

    private EventType eventType;
    private String uniqueId;
    private String oldResourceId;
    private String newResourceId;
    private String tag;
    private String value;
    private String value1;
    private String value2;
    private String value3;

    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    public EventType getEventType() {
        return eventType;
    }

    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }

    public String getNewResourceId() {
        return newResourceId;
    }

    public void setNewResourceId(String newResourceId) {
        this.newResourceId = newResourceId;
    }

    public String getOldResourceId() {
        return oldResourceId;
    }

    public void setOldResourceId(String oldResourceId) {
        this.oldResourceId = oldResourceId;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getValue1() {
        return value1;
    }

    public void setValue1(String value1) {
        this.value1 = value1;
    }

    public String getValue2() {
        return value2;
    }

    public void setValue2(String value2) {
        this.value2 = value2;
    }

    public String getValue3() {
        return value3;
    }

    public void setValue3(String value3) {
        this.value3 = value3;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}
