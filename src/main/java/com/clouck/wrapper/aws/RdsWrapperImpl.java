package com.clouck.wrapper.aws;
//package com.fleeio.dao;
//
//import java.util.List;
//
//import org.joda.time.DateTime;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import com.amazonaws.auth.AWSCredentials;
//import com.amazonaws.auth.BasicAWSCredentials;
//import com.amazonaws.services.rds.AmazonRDS;
//import com.amazonaws.services.rds.AmazonRDSClient;
//import com.amazonaws.services.rds.model.DescribeDBInstancesRequest;
//import com.amazonaws.services.rds.model.DescribeDBInstancesResult;
//import com.fleeio.converter.rds.RdsConverter;
//import com.fleeio.model.Account;
//import com.fleeio.model.Region;
//import com.fleeio.model.aws.rds.RdsDBInstanceVersion;
//
///**
// * This exists as a rds service isolation.
// * @author steng
// */
////TODO: properly catch all exceptions from all aws service call
//@Component
//public class RdsWrapperImpl implements RdsWrapper {
//	private static final Logger log = LoggerFactory.getLogger(RdsWrapperImpl.class);
//
//	@Autowired
//	private RdsConverter converter;
//	
//    private AmazonRDS findClient(Account account, Region region) {
//        // TODO: need to config client config parameter. ignore it for now.
//        // TODO: need a cached version based on account and region as key
//        AWSCredentials credential = new BasicAWSCredentials(account.getAccessKeyId(), account.getSecretAccessKey());
//        AmazonRDS rds = new AmazonRDSClient(credential);
//        rds.setEndpoint(region.toEndpoint());
//        return rds;
//    }
//
//	@Override
//    public List<RdsDBInstanceVersion> describeDBInstances(Account account, Region region, DateTime dt) {
//	    AmazonRDS rds = findClient(account, region);
//
//        DescribeDBInstancesRequest req = new DescribeDBInstancesRequest();
//
//        log.debug("start describing db instances for account:{} in region:{} via api", account.getId() + "=>" + account.getDisplayName(), region);
//        DescribeDBInstancesResult res = rds.describeDBInstances(req);
//
//        return converter.toRdsDBInstanceVersion(res.getDBInstances(), region, dt);
//    }
//}
