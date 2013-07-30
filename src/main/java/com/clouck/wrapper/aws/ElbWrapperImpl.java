package com.clouck.wrapper.aws;

import java.util.List;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.elasticloadbalancing.AmazonElasticLoadBalancing;
import com.amazonaws.services.elasticloadbalancing.AmazonElasticLoadBalancingClient;
import com.amazonaws.services.elasticloadbalancing.model.DescribeLoadBalancersRequest;
import com.amazonaws.services.elasticloadbalancing.model.DescribeLoadBalancersResult;
import com.clouck.converter.Ec2Converter;
import com.clouck.model.Account;
import com.clouck.model.Region;
import com.clouck.model.aws.AbstractResource;

@Component
public class ElbWrapperImpl implements ElbWrapper {
    private static final Logger log = LoggerFactory.getLogger(ElbWrapperImpl.class);

    @Autowired
    private Ec2Converter converter;

    private AmazonElasticLoadBalancing findClient(Account account, Region region) {
        // TODO: need to config client config parameter. ignore it for now.
        // TODO: need a cached version based on account and region as key
        AWSCredentials credential = new BasicAWSCredentials(account.getAccessKeyId(), account.getSecretAccessKey());
        AmazonElasticLoadBalancing elb = new AmazonElasticLoadBalancingClient(credential);
        elb.setRegion(com.amazonaws.regions.Region.getRegion(region.getRegions()));
        return elb;
    }

    @Override
    public List<AbstractResource<?>> describeLoadBalancers(Account account, Region region, DateTime dt) {
        AmazonElasticLoadBalancing elb = findClient(account, region);

        DescribeLoadBalancersRequest req = new DescribeLoadBalancersRequest();

        log.debug("start describing elb for account:{} in region:{} via api", account.getId() + "=>" + account.getName(), region);
        DescribeLoadBalancersResult res = elb.describeLoadBalancers(req);
        return converter.toLoadBalancers(res.getLoadBalancerDescriptions(), account.getId(), region, dt);
    }
}
