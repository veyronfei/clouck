package com.clouck.model.aws.comparator;

import java.util.Comparator;

import org.apache.commons.lang3.builder.CompareToBuilder;

import com.amazonaws.services.ec2.model.GroupIdentifier;

public class GroupIdentifierComparator extends AbstractComparator implements Comparator<GroupIdentifier> {

    @Override
    public int compare(GroupIdentifier gi1, GroupIdentifier gi2) {
        validateNotEqual(gi1.getGroupId(), gi2.getGroupId());
        return new CompareToBuilder().append(gi1.getGroupId(), gi2.getGroupId()).toComparison();
    }
}