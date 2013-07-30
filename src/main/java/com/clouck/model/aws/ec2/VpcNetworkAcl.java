package com.clouck.model.aws.ec2;

import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

import com.amazonaws.services.ec2.model.NetworkAcl;
import com.clouck.model.aws.AbstractResource;

@Document(collection = "vpc_network_acl")
@TypeAlias(value = "vpc_network_acl")
@SuppressWarnings("serial")
public class VpcNetworkAcl extends AbstractResource<NetworkAcl> {

    @Override
    protected boolean isEqual(AbstractResource newResource) {
        // TODO Auto-generated method stub
        return false;
    }
}

