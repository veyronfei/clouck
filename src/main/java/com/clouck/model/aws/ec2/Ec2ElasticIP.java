package com.clouck.model.aws.ec2;

import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

import com.amazonaws.services.ec2.model.Address;
import com.clouck.model.aws.AbstractResource;

@Document(collection = "ec2_elastic_ip")
@TypeAlias(value = "ec2_elastic_ip")
@SuppressWarnings("serial")
public class Ec2ElasticIP extends AbstractResource<Address> {

    @Override
    @SuppressWarnings("rawtypes")
    protected boolean isEqual(AbstractResource newResource) {
        Address oldAddress = this.getResource();
        Address newAddress = (Address) newResource.getResource();

        return oldAddress.equals(newAddress);
    }
}
