package com.clouck.model.aws.iam;

import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

import com.amazonaws.services.identitymanagement.model.User;
import com.clouck.model.aws.AbstractResource;

@Document(collection = "iam_user")
@TypeAlias(value = "iam_user")
@SuppressWarnings("serial")
public class IamUser extends AbstractResource<User> {

    @Override
    protected boolean isEqual(AbstractResource newResource) {
        // TODO Auto-generated method stub
        return false;
    }
}
