package com.clouck.wrapper.aws;

import java.util.List;

import org.joda.time.DateTime;

import com.clouck.model.Account;
import com.clouck.model.aws.AbstractResource;

public interface IamWrapper {

    List<AbstractResource<?>> listGroups(Account account, DateTime dt);

    List<AbstractResource<?>> listUsers(Account account, DateTime dt);

    List<AbstractResource<?>> listRoles(Account account, DateTime dt);

    String findUserId(String accessKeyId, String secretAccessKey);

}
