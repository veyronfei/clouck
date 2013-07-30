package com.clouck.model.aws.comparator;

import java.util.Comparator;

import org.apache.commons.lang3.builder.CompareToBuilder;

import com.amazonaws.services.ec2.model.InstancePrivateIpAddress;

public class InstancePrivateIpAddressComparator extends AbstractComparator implements Comparator<InstancePrivateIpAddress> {

    @Override
    public int compare(InstancePrivateIpAddress ipia1, InstancePrivateIpAddress ipia2) {
        validateNotEqual(ipia1.getPrivateIpAddress(), ipia2.getPrivateIpAddress());
        return new CompareToBuilder().append(ipia1.getPrivateIpAddress(), ipia2.getPrivateIpAddress()).toComparison();
    }
}