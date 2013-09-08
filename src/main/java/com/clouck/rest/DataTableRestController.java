package com.clouck.rest;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.clouck.converter.RepConverter;
import com.clouck.model.Region;
import com.clouck.model.ResourceType;
import com.clouck.model.aws.ec2.Ec2VersionMeta;
import com.clouck.rep.DataTableRep;
import com.clouck.service.AwsService;
import com.clouck.webapp.controller.AbstractController;

@Controller
@RequestMapping(value = "/rest/dataTable/accounts/{accountId}/ec2/versions")
public class DataTableRestController extends AbstractController {
    private static final Logger log = LoggerFactory.getLogger(DataTableRestController.class);

    @Autowired
    private AwsService awsService;

    @Autowired
    private RepConverter converter;

//    @RequestMapping(value = "/{ec2VersionId}", method = RequestMethod.GET)
//    public @ResponseBody DataTableRep showDataBable(@PathVariable String ec2VersionId) {
//        // TODO: do validation and what happend to account id and version list id is string or not this user
//        Collection<AbstractResource<?>> resources = awsService.findResources(ec2VersionId);
//        return converter.toDataTableData(resources);
//    }
//
//    @RequestMapping(value = "/{resourceType}/{timeStamp}/accounts/{accountId}", method = RequestMethod.GET)
//    public @ResponseBody DataTableRep showDataBable(@PathVariable String resourceType,
//            @PathVariable Long timeStamp, @PathVariable String accountId) {
//        // TODO: do validation and what happend to account id and version list id is string or not this user
//        log.debug("received call from account id:{}", accountId);
//        Collection<AbstractResource<?>> resources = awsService.findResources(accountId,
//                ResourceType.find(resourceType), new DateTime(timeStamp));
//        return converter.toDataTableData(resources);
//    }
    
    @RequestMapping(value = "/{resourceType}", method = RequestMethod.GET)
    public @ResponseBody DataTableRep showDataBable(@PathVariable String accountId, @PathVariable String resourceType,
            @RequestParam(value = "region", required = false) String regionEndpoint,
            @RequestParam Integer sEcho, @RequestParam Integer iDisplayStart,
            @RequestParam(required = false) Long ending, @RequestParam(required = false) String uniqueId,
            HttpServletRequest request) {
        String ctx = request.getContextPath();
        ResourceType rt = ResourceType.find(resourceType);
        Region region = findRegion(regionEndpoint);

        DateTime dt = new DateTime(ending);
        Pageable pageable = new PageRequest(iDisplayStart/10, 10);
        List<Ec2VersionMeta> ec2VersionMetas = awsService.findEc2VersionMetasOrderByTimeDetectedDesc(accountId, rt, region, pageable, dt, true, uniqueId);
        long numOfFilteredEc2VersionMetas = awsService.countEc2VersionMetas(accountId, rt, region, dt, true, uniqueId);
        long totalNumEc2VersionMetas = awsService.countEc2VersionMetas(accountId, rt);
        
        return converter.toDataTableData(accountId, resourceType, region, ec2VersionMetas, sEcho, totalNumEc2VersionMetas, numOfFilteredEc2VersionMetas, ctx);
    }
}