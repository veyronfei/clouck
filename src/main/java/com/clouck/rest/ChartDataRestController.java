//package com.clouck.rest;
//
//import java.util.List;
//
//import org.joda.time.DateTime;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//import com.clouck.converter.RepConverter;
//import com.clouck.exception.CloudVersionIllegalStateException;
//import com.clouck.model.Region;
//import com.clouck.model.ResourceType;
//import com.clouck.model.aws.ec2.Ec2Version;
//import com.clouck.rep.ChartDataRep;
//import com.clouck.service.AwsService;
//import com.clouck.webapp.controller.AbstractController;
//import com.google.common.base.Optional;
//
//@Controller
//@RequestMapping(value = "/rest/chart-data")
//public class ChartDataRestController extends AbstractController {
//    private static final Logger log = LoggerFactory.getLogger(ChartDataRestController.class);
//
//    @Autowired
//    private RepConverter converter;
//
//    @Autowired
//    private AwsService awsService;
//
//    @RequestMapping(value = "/{variableName}/{millis}", method = RequestMethod.GET)
//    public @ResponseBody ChartDataRep loadCharts(@PathVariable String variableName, @PathVariable long millis,
//            @RequestParam("account-id") String accountId, @RequestParam(value = "region", required = false) String regionEndpoint) {
//        // TODO: do validation
//        ResourceType resourceType = ResourceType.find(variableName);
//        log.debug("load charts from account id:{} of type:{}", accountId, resourceType);
//
//        Optional<Region> oRegion = Region.toRegion(regionEndpoint);
//        if (oRegion.isPresent()) {
//            List<Ec2Version> ec2Versions = awsService.findEc2VersionsUptoIncludeOrderByTimeDetected(accountId, resourceType, new DateTime(millis), oRegion.get());
//            return converter.toChartData(resourceType, ec2Versions);
//        } else {
//            throw new CloudVersionIllegalStateException("invalid region endpoint:" + regionEndpoint);
//        }
//    }
//}