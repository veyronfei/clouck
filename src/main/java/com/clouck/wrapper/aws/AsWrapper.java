package com.clouck.wrapper.aws;

import java.util.List;

import org.joda.time.DateTime;

import com.clouck.model.Account;
import com.clouck.model.Region;
import com.clouck.model.aws.AbstractResource;

public interface AsWrapper {

    List<AbstractResource<?>> describeLaunchConfigurations(Account account, Region region, DateTime dt);

    List<AbstractResource<?>> describeAutoScalingGroups(Account account, Region region, DateTime dt);
}
