package com.clouck.model.aws.ec2;

public enum Ec2InstanceState {
    Pending("pending"),
    Running("running"),
    Shutting_Down("shutting-down"),
    Terminated("terminated"),
    Stopping("stopping"),
    Stopped("stopped"),
    ;
    
    private String state;

    Ec2InstanceState(String state) {
        this.state = state;
    }

    public String getState() {
        return state;
    }
}
