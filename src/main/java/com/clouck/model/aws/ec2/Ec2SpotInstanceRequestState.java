package com.clouck.model.aws.ec2;

public enum Ec2SpotInstanceRequestState {
    Open("open"),
    Active("active"),
    Closed("closed"),
    Cancelled("cancelled"),
    Failed("failed"),
    ;
    
    private String state;

    Ec2SpotInstanceRequestState(String state) {
        this.state = state;
    }

    public String getState() {
        return state;
    }
}
