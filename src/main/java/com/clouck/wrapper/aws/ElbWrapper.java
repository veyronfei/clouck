package com.clouck.wrapper.aws;

import java.util.List;

import org.joda.time.DateTime;

import com.clouck.model.Account;
import com.clouck.model.Region;
import com.clouck.model.aws.AbstractResource;

public interface ElbWrapper {

    List<AbstractResource<?>> describeLoadBalancers(Account account, Region region, DateTime dt);
}
