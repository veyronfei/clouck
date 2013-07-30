package com.clouck.model.aws.ec2;

import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

import com.amazonaws.services.ec2.model.Vpc;
import com.clouck.model.aws.AbstractResource;

@Document(collection = "vpc_vpc")
@TypeAlias(value = "vpc_vpc")
@SuppressWarnings("serial")
public class VpcVpc extends AbstractResource<Vpc> {

    @Override
    protected boolean isEqual(AbstractResource newResource) {
        // TODO Auto-generated method stub
        return false;
    }
}
