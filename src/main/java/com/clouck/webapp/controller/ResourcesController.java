package com.clouck.webapp.controller;

import java.util.Collection;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.clouck.exception.ClouckInvalidParrameterException;
import com.clouck.exception.CloudVersionException;
import com.clouck.model.PageView;
import com.clouck.model.Region;
import com.clouck.model.ResourceType;
import com.clouck.model.aws.AbstractResource;
import com.clouck.model.aws.ec2.Ec2Version;
import com.clouck.service.AwsService;
import com.google.common.base.Optional;

@Controller
@RequestMapping("/resources")
public class ResourcesController extends AbstractController {
    private static final Logger log = LoggerFactory.getLogger(ResourcesController.class);

    @Autowired
    private AwsService awsService;


//    @SuppressWarnings("rawtypes")
//    @RequestMapping(value = "/{resourceType}/{millis}/{resourceId}/accounts/{accountId}", method = RequestMethod.GET)
//    public String load(@PathVariable String resourceType, @PathVariable long millis, @PathVariable String resourceId, @PathVariable String accountId, Model model) {
//        log.debug("load resources page..");
//        prep(accountId, model);
//        ResourceType rt = ResourceType.find(resourceType);
//        
//        Optional<AbstractResource> oResource = awsService.findResource(resourceId, ResourceType.find(resourceType));
//
//        if (oResource.isPresent()) {
//            model.addAttribute("ec2Resource", oResource.get());
//            model.addAttribute("millis", millis);
//            return rt.findResourcePage();
//        } else {
//            throw new CloudVersionException("invalid request, id:" + resourceId +
//                    "don't exist");
//        }
//    }
//
//    @RequestMapping(value = "/{resourceType}/{millis}/accounts/{accountId}", method = RequestMethod.GET)
//    public String loadSummary(@PathVariable String resourceType, @PathVariable long millis, @PathVariable String accountId, Model model) {
//        log.debug("load resources page..");
//        prep(accountId, model);
//
//        ResourceType rt = ResourceType.find(resourceType);
//        Collection<AbstractResource<?>> ec2Resources = awsService.findResources(accountId, ResourceType.find(resourceType), new DateTime(millis));
//        model.addAttribute("ec2Resources", ec2Resources);
//        model.addAttribute("millis", millis);
//        return rt.findSummaryVariableName();
//    }

//    @SuppressWarnings("rawtypes")
//    @RequestMapping(value = "/{resourceType}/accounts/{accountId}", method = RequestMethod.GET)
//    public String loadResources(@PathVariable String resourceType, @PathVariable String accountId, Model model) {
//        log.debug("load resources summary page..");
//        prep(accountId, model);
//        ResourceType rt = ResourceType.find(resourceType);
//        
//        awsService.findResources(ec2VersionId)
//        
//        Optional<AbstractResource> oResource = awsService.findResource(id, ResourceType.find(resourceType));
//
//        if (oResource.isPresent()) {
//            model.addAttribute("ec2Resource", oResource.get());
//        } else {
//            throw new CloudVersionException("invalid request, id:" + id +
//                    "don't exist");
//        }
//        return rt.findSingleVariableName();
//    }

//    @RequestMapping(value = "/{resourceType}/versions/{uniqueId}/accounts/{accountId}", method = RequestMethod.GET)
//    public String loadHistory(@PathVariable String resourceType, @PathVariable String uniqueId, @PathVariable String accountId, Model model) {
//        log.debug("load resources page..");
//        prep(accountId, model);
//        ResourceType rt = ResourceType.find(resourceType);
//        //TODO: PASS ACCOUNT ID AND REGION... TO MAKE SURE
//        List<? extends AbstractResource<?>> resources = awsService.findResources(ResourceType.find(resourceType), accountId, uniqueId);
//
//        model.addAttribute("ec2Resources", resources);
//        model.addAttribute("uniqueId", uniqueId);
//        return rt.findResourceHistoryPage();
//    }
//
//    @RequestMapping(value = "/{resourceType}/accounts/{accountId}", method = RequestMethod.GET)
//    public String loadHistory(@PathVariable String resourceType, @PathVariable String accountId, @RequestParam(value="view", required = false) PageView view, 
//            @RequestParam(value="region", required = false) Region region, Model model) {
//        log.debug("load resources page..");
//        if (view == null) {
//            view = PageView.Chart;
//        }
//        prep(accountId, model);
//        ResourceType rt = ResourceType.find(resourceType);
//        switch (view) {
//        case Table:
//            List<Ec2Version> ec2Versions = null;
//            if (region == null) {
//                ec2Versions = awsService.findEc2Versions(accountId, rt);
//            } else {
//                ec2Versions = awsService.findEc2Versions(accountId, rt, region);
//            }
//            model.addAttribute("ec2Versions", ec2Versions);
//            return rt.findHistoryVariableName();
//        case Chart:
//            return rt.findVariableName();
//        default:
//            throw new ClouckInvalidParrameterException("view:" + view + " is not recogonized");
//        }
//    }
}
