package com.clouck.wrapper.aws;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.clouck.converter.S3Converter;
import com.clouck.model.Account;

/**
 * This exists as a s3 service isolation.
 * @author steng
 */
//TODO: properly catch all exceptions from all aws service call
@Component
public class S3WrapperImpl implements S3Wrapper {
	private static final Logger log = LoggerFactory.getLogger(S3WrapperImpl.class);

	@Autowired
	private S3Converter converter;
	
    private AmazonS3 findClient(Account account) {
        //TODO: need to config client config parameter. ignore it for now.
        //TODO: need a cached version based on account and region as key
        AWSCredentials credential = new BasicAWSCredentials(account.getAccessKeyId(), account.getSecretAccessKey());
        AmazonS3 s3 = new AmazonS3Client(credential);
        return s3;
    }
//
//    @Override
//    public List<AbstractTaskRecord> listBuckets(Account account) {
//        AmazonS3 s3 = findClient(account);
//        
//        List<AbstractTaskRecord> records = new ArrayList<AbstractTaskRecord>();
//        
//        ListBucketsRequest req = new ListBucketsRequest();
//        
//        List<Bucket> buckets= s3.listBuckets(req);
//        log.debug("find {} buckets via api.", buckets.size());
//        for (Bucket b : buckets) {
//            records.add(listObjects(account, b.getName()));
//        }
//
//        return records;
//    }
//
//    @Override
//    public S3ListBucketsTaskRecord listObjects(Account account, String bucketName) {
//        AmazonS3 s3 = findClient(account);
//        
//        ListObjectsRequest req = new ListObjectsRequest().withBucketName(bucketName);
//        
//        ObjectListing result = s3.listObjects(req);
//        int numOfObjects = result.getObjectSummaries().size();
//        long bucketSize = 0;
//        for (S3ObjectSummary object : result.getObjectSummaries()) {
//            bucketSize = bucketSize + object.getSize();
//        }
//        while (result.isTruncated()) {
//            result = s3.listNextBatchOfObjects(result);
//            numOfObjects = numOfObjects + result.getObjectSummaries().size();
//            for (S3ObjectSummary object : result.getObjectSummaries()) {
//                bucketSize = bucketSize + object.getSize();
//            }
//        }
//        S3ListBucketsTaskRecord taskResult = new S3ListBucketsTaskRecord();
//        taskResult.setBucketName(bucketName);
//        taskResult.setBucketSize(bucketSize);
//        taskResult.setNumOfObjects(numOfObjects);
//        return taskResult;
//    }
}
