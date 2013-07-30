package com.clouck.model.aws.comparator;

import java.util.Comparator;

import org.apache.commons.lang3.builder.CompareToBuilder;

import com.amazonaws.services.ec2.model.InstanceBlockDeviceMapping;

public class InstanceBlockDeviceMappingComparator implements Comparator<InstanceBlockDeviceMapping> {

    @Override
    public int compare(InstanceBlockDeviceMapping ibdm1, InstanceBlockDeviceMapping ibdm2) {
        return new CompareToBuilder().append(ibdm1.getDeviceName(), ibdm2.getDeviceName()).toComparison();
    }
}