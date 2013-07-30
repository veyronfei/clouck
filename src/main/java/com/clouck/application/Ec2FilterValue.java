package com.clouck.application;

public enum Ec2FilterValue {
    ;

    private String value;
    
    Ec2FilterValue(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
