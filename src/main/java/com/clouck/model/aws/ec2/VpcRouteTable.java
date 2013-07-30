package com.clouck.model.aws.ec2;

import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

import com.amazonaws.services.ec2.model.RouteTable;
import com.clouck.model.aws.AbstractResource;

@Document(collection = "vpc_route_table")
@TypeAlias(value = "vpc_route_table")
@SuppressWarnings("serial")
public class VpcRouteTable extends AbstractResource<RouteTable> {

    @Override
    protected boolean isEqual(AbstractResource newResource) {
        // TODO Auto-generated method stub
        return false;
    }
}

