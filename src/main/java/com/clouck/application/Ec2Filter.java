package com.clouck.application;

import java.util.ArrayList;
import java.util.List;

public class Ec2Filter {

    private String name;
    private List<String> values = new ArrayList<String>();
    
    public Ec2Filter withName(Ec2FilterName name) {
        this.name = name.getKey();
        return this;
    }
    
    public Ec2Filter withValue(Ec2FilterValue value) {
        values.add(value.getValue());
        return this;
    }

    public Ec2Filter withValue(String value) {
        values.add(value);
        return this;
    }

    public List<String> getValues() {
        return values;
    }

    public String getName() {
        return name;
    }
}
