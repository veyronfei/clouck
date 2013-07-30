package com.clouck.model.aws.ec2;

import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

import com.amazonaws.services.ec2.model.InternetGateway;
import com.clouck.model.aws.AbstractResource;

@Document(collection = "vpc_internet_gateway")
@TypeAlias(value = "vpc_internet_gateway")
@SuppressWarnings("serial")
public class VpcInternetGateway extends AbstractResource<InternetGateway> {

    @Override
    protected boolean isEqual(AbstractResource newResource) {
        // TODO Auto-generated method stub
        return false;
    }
}
