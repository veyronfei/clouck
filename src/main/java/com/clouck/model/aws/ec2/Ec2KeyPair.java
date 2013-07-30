package com.clouck.model.aws.ec2;

import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

import com.amazonaws.services.ec2.model.KeyPairInfo;
import com.clouck.model.aws.AbstractResource;

@Document(collection = "ec2_key_pair")
@TypeAlias(value = "ec2_key_pair")
@SuppressWarnings("serial")
public class Ec2KeyPair extends AbstractResource<KeyPairInfo> {

    @Override
    @SuppressWarnings("rawtypes")
    protected boolean isEqual(AbstractResource newResource) {
        KeyPairInfo oldKeyPairInfo = this.getResource();
        KeyPairInfo newKeyPairInfo = (KeyPairInfo) newResource.getResource();

        return oldKeyPairInfo.equals(newKeyPairInfo);
    }
}

