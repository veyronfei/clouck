package com.clouck.service;

import java.util.Collection;
import java.util.HashSet;

import com.clouck.model.aws.AbstractResource;

public class CompareResourceResult<V extends AbstractResource<?>> {
    private Collection<V> addedResources = new HashSet<>();
    private Collection<String> unchangedResourceIds = new HashSet<>();
    private Collection<String> deletedResourceIds = new HashSet<>();

    public Collection<V> getAddedResources() {
        return addedResources;
    }

    public Collection<String> getUnchangedResourceIds() {
        return unchangedResourceIds;
    }

    public Collection<String> getDeletedResourceIds() {
        return deletedResourceIds;
    }
}
