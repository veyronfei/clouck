package com.clouck.model.aws.iam;

import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

import com.amazonaws.services.identitymanagement.model.Group;
import com.clouck.model.aws.AbstractResource;

@Document(collection = "iam_group")
@TypeAlias(value = "iam_group")
@SuppressWarnings("serial")
public class IamGroup extends AbstractResource<Group> {

    @Override
    protected boolean isEqual(AbstractResource newResource) {
        // TODO Auto-generated method stub
        return false;
    }
}
