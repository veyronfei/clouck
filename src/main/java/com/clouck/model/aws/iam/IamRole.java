package com.clouck.model.aws.iam;

import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

import com.amazonaws.services.identitymanagement.model.Role;
import com.clouck.model.aws.AbstractResource;

@Document(collection = "iam_role")
@TypeAlias(value = "iam_role")
@SuppressWarnings("serial")
public class IamRole extends AbstractResource<Role> {

    @Override
    protected boolean isEqual(AbstractResource newResource) {
        // TODO Auto-generated method stub
        return false;
    }
}
