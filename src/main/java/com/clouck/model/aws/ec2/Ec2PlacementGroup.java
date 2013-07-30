package com.clouck.model.aws.ec2;

import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

import com.amazonaws.services.ec2.model.PlacementGroup;
import com.clouck.model.aws.AbstractResource;

@Document(collection = "ec2_placement_group")
@TypeAlias(value = "ec2_placement_group")
@SuppressWarnings("serial")
public class Ec2PlacementGroup extends AbstractResource<PlacementGroup> {

    @Override
    @SuppressWarnings("rawtypes")
    protected boolean isEqual(AbstractResource newResource) {
        PlacementGroup oldPlacementGroup = this.getResource();
        PlacementGroup newPlacementGroup = (PlacementGroup) newResource.getResource();

        return oldPlacementGroup.equals(newPlacementGroup);
    }
}

