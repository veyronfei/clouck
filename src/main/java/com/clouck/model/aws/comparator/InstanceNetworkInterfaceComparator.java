package com.clouck.model.aws.comparator;

import java.util.Comparator;

import org.apache.commons.lang3.builder.CompareToBuilder;

import com.amazonaws.services.ec2.model.InstanceNetworkInterface;

public class InstanceNetworkInterfaceComparator implements Comparator<InstanceNetworkInterface> {

    @Override
    public int compare(InstanceNetworkInterface ini1, InstanceNetworkInterface ini2) {
        return new CompareToBuilder().append(ini1.getNetworkInterfaceId(), ini2.getNetworkInterfaceId()).toComparison();
    }
}