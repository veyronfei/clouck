package com.clouck.model.aws.ec2;

import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

import com.amazonaws.services.ec2.model.Subnet;
import com.clouck.model.aws.AbstractResource;

@Document(collection = "vpc_subnet")
@TypeAlias(value = "vpc_subnet")
@SuppressWarnings("serial")
public class VpcSubnet extends AbstractResource<Subnet> {

    @Override
    protected boolean isEqual(AbstractResource newResource) {
        // TODO Auto-generated method stub
        return false;
    }
}
