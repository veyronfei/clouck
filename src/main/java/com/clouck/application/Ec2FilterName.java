package com.clouck.application;

public enum Ec2FilterName {
    TAG_KEY("tag-key"),
    ASSOCIATION_MAIN("association.main"),
    INSTANCE_STATE_NAME("instance-state-name"),
    ATTACHMENT_VPC_ID("attachment.vpc-id"),
    VPC_ID("vpc-id"),
    OWNER_ID("owner-id"),
    IMAGE_ID("image-id"),
    SNAPSHOT_ID("snapshot-id"),
    DEFAULT("default"),
    STATE("state"),
    ROOT_DEVICE_TYPE("root-device-type");

    private String key;
    
    Ec2FilterName(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}
