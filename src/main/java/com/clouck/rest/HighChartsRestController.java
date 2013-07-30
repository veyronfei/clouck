package com.clouck.rest;

import java.util.List;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.clouck.converter.RepConverter;
import com.clouck.model.ResourceType;
import com.clouck.model.aws.ec2.Ec2Version;
import com.clouck.rep.HighChartsRep;
import com.clouck.service.AwsService;
import com.clouck.webapp.controller.AbstractController;

@Controller
@RequestMapping(value = "/rest/highCharts")
public class HighChartsRestController extends AbstractController {
    private static final Logger log = LoggerFactory.getLogger(HighChartsRestController.class);

    @Autowired
    private RepConverter converter;

    @Autowired
    private AwsService awsService;

//    @RequestMapping(value = "/{variableName}/{timeStamp}/accounts/{accountId}", method = RequestMethod.GET)
//    public @ResponseBody HighChartsRep showHighCharts(@PathVariable String variableName,
//            @PathVariable long timeStamp, @PathVariable String accountId) {
//        // TODO: do validation
//        log.debug("received call from account id:{}", accountId);
//
//        ResourceType resourceType = ResourceType.find(variableName);
//        List<Ec2Version> ec2Versions = awsService.findEc2VersionsUpto(accountId, resourceType, new DateTime(timeStamp));
//
//        return converter.toHigCharts(resourceType, ec2Versions);
//    }
}