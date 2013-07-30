package com.clouck.model.aws.comparator;

import java.util.Comparator;

import org.apache.commons.lang3.builder.CompareToBuilder;

import com.amazonaws.services.ec2.model.PrivateIpAddressSpecification;

public class PrivateIpAddressSpecificationComparator implements Comparator<PrivateIpAddressSpecification> {

    @Override
    public int compare(PrivateIpAddressSpecification pias1, PrivateIpAddressSpecification pias2) {
        return new CompareToBuilder().append(pias1.getPrivateIpAddress(), pias2.getPrivateIpAddress()).toComparison();
    }
}