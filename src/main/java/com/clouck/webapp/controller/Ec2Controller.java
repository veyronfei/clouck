package com.clouck.webapp.controller;

import java.util.List;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.clouck.converter.RepConverter;
import com.clouck.exception.CloudVersionException;
import com.clouck.model.Region;
import com.clouck.model.ResourceType;
import com.clouck.model.aws.AbstractResource;
import com.clouck.model.aws.ec2.Ec2VersionMeta;
import com.clouck.service.AwsService;
import com.clouck.service.EventService;
import com.google.common.base.Optional;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Controller
@RequestMapping("/accounts/{accountId}/ec2")
public class Ec2Controller extends AbstractController {
    private static final Logger log = LoggerFactory.getLogger(Ec2Controller.class);

    @Autowired
    private AwsService awsService;
    @Autowired
    private UserPreference userPreference;
    @Autowired
    private EventService eventService;
    @Autowired
    private RepConverter converter;

    @RequestMapping(value = "/overview", method = RequestMethod.GET)
    public String loadOverview(Model model, @PathVariable String accountId,
            @RequestParam(value = "region", required = false) String regionEndpoint) {
        log.debug("loading overview page");

        prep(accountId, regionEndpoint, model);
        Region region = findRegion(regionEndpoint);

        Pageable pageable = new PageRequest(0, 10);
        List<Ec2VersionMeta> ec2VersionMetas = awsService.findEc2VersionMetasOrderByTimeDetectedDesc(accountId, region, pageable);
        model.addAttribute("ec2VersionMetaReps", converter.toEc2VersionMetaReps(ec2VersionMetas));

        for (ResourceType rt : ResourceType.findViewResourceTypes()) {
            model.addAttribute(rt.findVariableName(), awsService.findLatestResourceSizes(accountId, rt, region));
        }
        
        model.addAttribute("millis", DateTime.now().toInstant().getMillis());
        return "ec2-overview";
    }

    @RequestMapping(value = "/{resourceType}/{resourceId}/history", method = RequestMethod.GET)
    public String loadHistory(Model model, @PathVariable String accountId,
            @PathVariable String resourceType, @PathVariable String resourceId,
            @RequestParam(value = "region", required = false) String regionEndpoint) {
        log.debug("load resource history page..");

        prep(accountId, regionEndpoint, model);
        ResourceType rt = ResourceType.find(resourceType);
        Region region = findRegion(regionEndpoint);
        @SuppressWarnings("rawtypes")
        Optional<AbstractResource> oResource = awsService.findResource(resourceId, ResourceType.find(resourceType));

        if (oResource.isPresent()) {
            String uniqueId = oResource.get().getUniqueId();
            //TODO: this is incorrect, as uniqueId could be two ids combination
            List<Ec2VersionMeta> ec2VersionMetas = awsService.findEc2VersionMetasOrderByTimeDetectedDesc(accountId, region, rt, uniqueId);
            model.addAttribute("ec2ResourceReps", converter.toEc2ResourceReps(ec2VersionMetas, uniqueId));
            model.addAttribute("resourceId", resourceId);
            model.addAttribute("uniqueId", uniqueId);
            model.addAttribute("numOfEc2VersionMetas", ec2VersionMetas.size());
        } else {
            throw new CloudVersionException("invalid request, id:" + resourceId + "don't exist");
        }

        return rt.findResourceHistoryPage();
    }

    @RequestMapping(value = "/{resourceType}", method = RequestMethod.GET)
    public String loadCurrentResources(Model model, @PathVariable String accountId,
            @PathVariable String resourceType,
            @RequestParam(required = false) String format,
            @RequestParam(required = true) Long at,
            @RequestParam(value = "region", required = false) String regionEndpoint) {
        ResourceType rt = ResourceType.find(resourceType);
        log.debug("loading {} page", rt);
        prep(accountId, regionEndpoint, model);
        Region region = findRegion(regionEndpoint);
        DateTime dt = new DateTime(at);

        List<AbstractResource<?>> ec2Resources = awsService.findResourcesAt(accountId, rt, region, dt);
        model.addAttribute("ec2Resources", ec2Resources);
        model.addAttribute("numOfEc2VersionMetas", awsService.countEc2VersionMetas(accountId, rt, region));
        model.addAttribute("at", dt.toDate());

        if (format == null) {
            return rt.findResourcesPage();
        } else {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String ec2ResourcesJson = gson.toJson(converter.toResources(ec2Resources));
            model.addAttribute("ec2ResourcesJson", ec2ResourcesJson);
            return rt.findResourcesRawPage();
        }
    }

    @RequestMapping(value = "/{resourceType}/{resourceId}", method = RequestMethod.GET)
    public String loadResource(Model model, @PathVariable String accountId,
            @PathVariable String resourceType,
            @PathVariable String resourceId,
            @RequestParam(value = "region", required = false) String regionEndpoint,
            @RequestParam long at,
            @RequestParam(required = false) String format) {
        log.debug("loading resources page..");
        prep(accountId, regionEndpoint, model);
        Region region = findRegion(regionEndpoint);
        ResourceType rt = ResourceType.find(resourceType);
        DateTime dt = new DateTime(at);

        @SuppressWarnings("rawtypes")
        Optional<AbstractResource> oResource = awsService.findResource(resourceId, ResourceType.find(resourceType));

        if (oResource.isPresent()) {
            model.addAttribute("ec2Resource", oResource.get());
            model.addAttribute("at", dt.toDate());
            if (format == null) {
                String uniqueId = oResource.get().getUniqueId();
                long numOfEc2VersionMetas = awsService.countEc2VersionMetas(accountId, rt, region, uniqueId);
                model.addAttribute("numOfEc2VersionMetas", numOfEc2VersionMetas);
                model.addAttribute("ownerId", accountService.find(accountId).get().getAccountNumber());
                return rt.findResourcePage();
            } else {
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                String ec2ResourceJson = gson.toJson(oResource.get().getResource());
                model.addAttribute("ec2ResourceJson", ec2ResourceJson);
                return rt.findResourceRawPage();
            }
        } else {
            throw new CloudVersionException("invalid request, id:" + resourceId + "don't exist");
        }
    }

    @RequestMapping(value = "/{resourceType}/versions", method = RequestMethod.GET)
    public String loadResourcesHistory(Model model, @PathVariable String accountId,
            @PathVariable String resourceType,
            @RequestParam(value = "region", required = false) String regionEndpoint) {
        log.debug("loading resources page..");
        prep(accountId, regionEndpoint, model);
        ResourceType rt = ResourceType.find(resourceType);
        Region region = findRegion(regionEndpoint);

//        model.addAttribute("numOfEc2VersionMetas", awsService.countEc2VersionMetas(accountId, rt, region));
        return rt.findResourcesHistoryPage();
    }
}
