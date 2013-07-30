package com.clouck.model.aws.comparator;

import java.util.Comparator;

import org.apache.commons.lang3.builder.CompareToBuilder;

import com.amazonaws.services.ec2.model.Tag;

public class TagComparator implements Comparator<Tag> {

    @Override
    public int compare(Tag t1, Tag t2) {
        return new CompareToBuilder().append(t1.getKey(), t2.getKey()).toComparison();
    }
}