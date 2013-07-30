package com.clouck.model;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

@SuppressWarnings("serial")
public abstract class AbstractModel implements Serializable {

    private String id;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}