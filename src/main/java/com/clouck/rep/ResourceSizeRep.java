package com.clouck.rep;

public class ResourceSizeRep {

    private int instance_size;
    
    private int security_group_size;
    
    private int snapshot_size;
    
    private int image_size;
    
    private int volume_size;

    public int getInstance_size() {
        return instance_size;
    }

    public void setInstance_size(int instance_size) {
        this.instance_size = instance_size;
    }

    public int getSecurity_group_size() {
        return security_group_size;
    }

    public void setSecurity_group_size(int security_group_size) {
        this.security_group_size = security_group_size;
    }

    public int getSnapshot_size() {
        return snapshot_size;
    }

    public void setSnapshot_size(int snapshot_size) {
        this.snapshot_size = snapshot_size;
    }

    public int getImage_size() {
        return image_size;
    }

    public void setImage_size(int image_size) {
        this.image_size = image_size;
    }

    public int getVolume_size() {
        return volume_size;
    }

    public void setVolume_size(int volume_size) {
        this.volume_size = volume_size;
    }

}
