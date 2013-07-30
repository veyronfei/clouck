package com.clouck.wrapper.aws;

import java.util.List;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.identitymanagement.AmazonIdentityManagement;
import com.amazonaws.services.identitymanagement.AmazonIdentityManagementClient;
import com.amazonaws.services.identitymanagement.model.GetUserResult;
import com.amazonaws.services.identitymanagement.model.ListGroupsRequest;
import com.amazonaws.services.identitymanagement.model.ListGroupsResult;
import com.amazonaws.services.identitymanagement.model.ListRolesRequest;
import com.amazonaws.services.identitymanagement.model.ListRolesResult;
import com.amazonaws.services.identitymanagement.model.ListUsersRequest;
import com.amazonaws.services.identitymanagement.model.ListUsersResult;
import com.clouck.converter.IamConverter;
import com.clouck.model.Account;
import com.clouck.model.aws.AbstractResource;

@Component
public class IamWrapperImpl implements IamWrapper {
    private static final Logger log = LoggerFactory.getLogger(IamWrapperImpl.class);

    @Autowired
    private IamConverter converter;

    private AmazonIdentityManagement findClient(Account account) {
        return findClient(account.getAccessKeyId(), account.getSecretAccessKey());
    }

    private AmazonIdentityManagement findClient(String accessKeyId, String secretAccessKey) {
        // TODO: need to config client config parameter. ignore it for now.
        AWSCredentials credential = new BasicAWSCredentials(accessKeyId, secretAccessKey);
        AmazonIdentityManagement iam = new AmazonIdentityManagementClient(credential);
//        iam.setEndpoint(Region.toIamEndpoint());
        return iam;
    }

    @Override
    public List<AbstractResource<?>> listGroups(Account account, DateTime dt) {
        AmazonIdentityManagement iam = findClient(account);

        ListGroupsRequest req = new ListGroupsRequest();

        log.debug("start list groups for account:{} via api", account.getId() + "=>" + account.getName());
        ListGroupsResult res = iam.listGroups(req);

        return converter.toIamGroups(res.getGroups(), account.getId(), dt);
    }
    
    @Override
    public List<AbstractResource<?>> listUsers(Account account, DateTime dt) {
        AmazonIdentityManagement iam = findClient(account);

        ListUsersRequest req = new ListUsersRequest();

        log.debug("start list users for account:{} via api", account.getId() + "=>" + account.getName());
        ListUsersResult res = iam.listUsers(req);

        return converter.toIamUsers(res.getUsers(), account.getId(), dt);
    }
    
    @Override
    public List<AbstractResource<?>> listRoles(Account account, DateTime dt) {
        AmazonIdentityManagement iam = findClient(account);

        ListRolesRequest req = new ListRolesRequest();

        log.debug("start list roles for account:{} via api", account.getId() + "=>" + account.getName());
        ListRolesResult res = iam.listRoles(req);

        return converter.toIamRoles(res.getRoles(), account.getId(), dt);
    }

    @Override
    public String findUserId(String accessKeyId, String secretAccessKey) {
        AmazonIdentityManagement iam = findClient(accessKeyId, secretAccessKey);

        GetUserResult res = iam.getUser();

        return res.getUser().getUserId();
    }
}
