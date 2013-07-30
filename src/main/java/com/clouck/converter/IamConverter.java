package com.clouck.converter;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.amazonaws.services.identitymanagement.model.Group;
import com.amazonaws.services.identitymanagement.model.Role;
import com.amazonaws.services.identitymanagement.model.User;
import com.clouck.model.aws.AbstractResource;
import com.clouck.model.aws.iam.IamGroup;
import com.clouck.model.aws.iam.IamRole;
import com.clouck.model.aws.iam.IamUser;

@Component
public class IamConverter {
    private static final Logger log = LoggerFactory.getLogger(IamConverter.class);

    private void conf(AbstractResource<?> resource, String accountId, DateTime dateTime) {
        resource.setAccountId(accountId);
        resource.setTimeDetected(dateTime.toDate());
    }

    public List<AbstractResource<?>> toIamGroups(List<Group> groups, String accountId, DateTime dt) {
        List<AbstractResource<?>> resources = new ArrayList<>();
        for (Group group : groups) {
            IamGroup iamGroup = new IamGroup();
            conf(iamGroup, accountId, dt);
            iamGroup.setResource(group);
            resources.add(iamGroup);
        }
        log.debug("{} groups found via api and converted to IamGroup", resources.size());
        return resources;
    }

    public List<AbstractResource<?>> toIamUsers(List<User> users, String accountId, DateTime dt) {
        List<AbstractResource<?>> resources = new ArrayList<>();
        for (User user : users) {
            IamUser iamUser = new IamUser();
            conf(iamUser, accountId, dt);
            iamUser.setResource(user);
            resources.add(iamUser);
        }
        log.debug("{} users found via api and converted to IamUser", resources.size());
        return resources;
    }

    public List<AbstractResource<?>> toIamRoles(List<Role> roles, String accountId, DateTime dt) {
        List<AbstractResource<?>> resources = new ArrayList<>();
        for (Role role : roles) {
            IamRole iamRole = new IamRole();
            conf(iamRole, accountId, dt);
            iamRole.setResource(role);
            resources.add(iamRole);
        }
        log.debug("{} roles found via api and converted to IamRole", resources.size());
        return resources;
    }
}
