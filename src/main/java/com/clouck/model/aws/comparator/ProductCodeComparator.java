package com.clouck.model.aws.comparator;

import java.util.Comparator;

import org.apache.commons.lang3.builder.CompareToBuilder;

import com.amazonaws.services.ec2.model.ProductCode;

public class ProductCodeComparator implements Comparator<ProductCode> {

    @Override
    public int compare(ProductCode p1, ProductCode p2) {
        return new CompareToBuilder().append(p1.getProductCodeId(), p2.getProductCodeId()).toComparison();
    }
}