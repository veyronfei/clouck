package com.clouck.service;

import java.util.List;

import org.joda.time.DateTime;

import com.clouck.model.Account;
import com.clouck.model.Region;
import com.clouck.model.ResourceType;
import com.clouck.model.aws.AbstractResource;

public interface ResourceService {

    boolean addNewResources(Account account, Region region, ResourceType resourceType,
            List<AbstractResource<?>> newResources, DateTime dt);

    void convertEc2Reservation2Ec2Instance(Account account);

    List<AbstractResource<?>> findNewResources(Account account, Region region, ResourceType resourceType, DateTime dt);

    void scanAccount(Account account);
}
