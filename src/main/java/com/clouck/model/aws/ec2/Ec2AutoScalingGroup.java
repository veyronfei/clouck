package com.clouck.model.aws.ec2;

import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

import com.amazonaws.services.autoscaling.model.AutoScalingGroup;
import com.clouck.model.aws.AbstractResource;

@Document(collection = "ec2_auto_scaling_group")
@TypeAlias(value = "ec2_auto_scaling_group")
@SuppressWarnings("serial")
public class Ec2AutoScalingGroup extends AbstractResource<AutoScalingGroup> {

    @Override
    protected boolean isEqual(AbstractResource newResource) {
        // TODO Auto-generated method stub
        return false;
    }
}

