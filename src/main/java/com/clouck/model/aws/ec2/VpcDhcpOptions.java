package com.clouck.model.aws.ec2;

import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

import com.amazonaws.services.ec2.model.DhcpOptions;
import com.clouck.model.aws.AbstractResource;

@Document(collection = "vpc_dhcp_options")
@TypeAlias(value = "vpc_dhcp_options")
@SuppressWarnings("serial")
public class VpcDhcpOptions extends AbstractResource<DhcpOptions> {

    @Override
    protected boolean isEqual(AbstractResource newResource) {
        // TODO Auto-generated method stub
        return false;
    }
}
