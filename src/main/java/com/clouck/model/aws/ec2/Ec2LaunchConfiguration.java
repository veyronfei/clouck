package com.clouck.model.aws.ec2;

import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

import com.amazonaws.services.autoscaling.model.LaunchConfiguration;
import com.clouck.model.aws.AbstractResource;

@Document(collection = "ec2_launch_configuration")
@TypeAlias(value = "ec2_launch_configuration")
@SuppressWarnings("serial")
public class Ec2LaunchConfiguration extends AbstractResource<LaunchConfiguration> {

    @Override
    protected boolean isEqual(AbstractResource newResource) {
        // TODO Auto-generated method stub
        return false;
    }
}

