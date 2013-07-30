package com.clouck.model.aws.ec2;

import org.apache.commons.lang3.builder.EqualsBuilder;

public class Ec2InstanceAttribute {
    private Boolean terminationProtection;
    private String shutdownBehavior;
    private String userData;

    public Ec2InstanceAttribute(Boolean terminationProtection, String shutdownBehavior, String userData) {
        this.terminationProtection = terminationProtection;
        this.shutdownBehavior = shutdownBehavior;
        this.userData = userData;
    }

    public Boolean getTerminationProtection() {
        return terminationProtection;
    }

    public void setTerminationProtection(Boolean terminationProtection) {
        this.terminationProtection = terminationProtection;
    }

    public String getShutdownBehavior() {
        return shutdownBehavior;
    }

    public void setShutdownBehavior(String shutdownBehavior) {
        this.shutdownBehavior = shutdownBehavior;
    }

    public String getUserData() {
        return userData;
    }

    public void setUserData(String userData) {
        this.userData = userData;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;

        if (obj instanceof Ec2InstanceAttribute == false) return false;
        Ec2InstanceAttribute other = (Ec2InstanceAttribute) obj;

        return new EqualsBuilder().append(terminationProtection, other.getTerminationProtection()).
                append(shutdownBehavior, other.getShutdownBehavior()).
                append(userData, other.getUserData()).isEquals();
    }
}
