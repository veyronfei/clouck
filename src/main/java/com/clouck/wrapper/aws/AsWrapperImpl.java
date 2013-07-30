package com.clouck.wrapper.aws;

import java.util.List;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.autoscaling.AmazonAutoScaling;
import com.amazonaws.services.autoscaling.AmazonAutoScalingClient;
import com.amazonaws.services.autoscaling.model.DescribeAutoScalingGroupsRequest;
import com.amazonaws.services.autoscaling.model.DescribeAutoScalingGroupsResult;
import com.amazonaws.services.autoscaling.model.DescribeLaunchConfigurationsRequest;
import com.amazonaws.services.autoscaling.model.DescribeLaunchConfigurationsResult;
import com.clouck.converter.Ec2Converter;
import com.clouck.model.Account;
import com.clouck.model.Region;
import com.clouck.model.aws.AbstractResource;

@Component
public class AsWrapperImpl implements AsWrapper {
    private static final Logger log = LoggerFactory.getLogger(AsWrapperImpl.class);

    @Autowired
    private Ec2Converter converter;

    private AmazonAutoScaling findClient(Account account, Region region) {
        // TODO: need to config client config parameter. ignore it for now.
        // TODO: need a cached version based on account and region as key
        AWSCredentials credential = new BasicAWSCredentials(account.getAccessKeyId(), account.getSecretAccessKey());
        AmazonAutoScaling as = new AmazonAutoScalingClient(credential);
//        as.setEndpoint(region.toAsEndpoint());
        return as;
    }

    @Override
    public List<AbstractResource<?>> describeLaunchConfigurations(Account account, Region region, DateTime dt) {
        AmazonAutoScaling as = findClient(account, region);

        DescribeLaunchConfigurationsRequest req = new DescribeLaunchConfigurationsRequest();
        log.debug("start describing instances for account:{} in region:{} via api", account.getId() + "=>" + account.getName(), region);
        DescribeLaunchConfigurationsResult res = as.describeLaunchConfigurations(req);

        return converter.toAsLaunchConfigurations(res.getLaunchConfigurations(), account.getId(), region, dt);
    }

    @Override
    public List<AbstractResource<?>> describeAutoScalingGroups(Account account, Region region, DateTime dt) {
        AmazonAutoScaling as = findClient(account, region);

        DescribeAutoScalingGroupsRequest req = new DescribeAutoScalingGroupsRequest();
        log.debug("start describing auto scaling groups for account:{} in region:{} via api", account.getId() + "=>" + account.getName(), region);
        DescribeAutoScalingGroupsResult res = as.describeAutoScalingGroups(req);

        return converter.toAsGroups(res.getAutoScalingGroups(), account.getId(), region, dt);
    }
}
