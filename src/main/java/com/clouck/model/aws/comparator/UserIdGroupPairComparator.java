package com.clouck.model.aws.comparator;

import java.util.Comparator;

import org.apache.commons.lang3.builder.CompareToBuilder;

import com.amazonaws.services.ec2.model.UserIdGroupPair;

public class UserIdGroupPairComparator implements Comparator<UserIdGroupPair> {

    @Override
    public int compare(UserIdGroupPair uig1, UserIdGroupPair uig2) {
        return new CompareToBuilder().append(uig1.getGroupId(), uig2.getGroupId()).toComparison();
    }
}