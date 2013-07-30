package com.clouck.rest;
//package com.fleeio.rest;
//
//import java.io.IOException;
//import java.util.List;
//
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpServletResponseWrapper;
//
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
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
//import com.fleeio.converter.rep.RepConverter;
//import com.fleeio.exception.CloudVersionException;
//import com.fleeio.model.AbstractEvent;
//import com.fleeio.model.Account;
//import com.fleeio.rep.EventRep;
//import com.fleeio.rep.ResourceSizeRep;
//import com.fleeio.service.AwsService;
//import com.fleeio.service.EventService;
//import com.fleeio.webapp.controller.AbstractController;
//
//@Controller
//@RequestMapping(value = "/rest/accounts")
//public class RestController extends AbstractController {
//    private static final Logger log = LoggerFactory.getLogger(RestController.class);
//
//    @Autowired
//    private EventService eventService;
//    
//    @Autowired
//    private RepConverter converter;
//    
//    @Autowired
//    private AwsService awsService;
//    
////    @RequestMapping(value = "/routes/{registryShortName}", method = RequestMethod.GET)
////    public @ResponseBody List<RouteRep> doGet(@PathVariable String registryShortName) {
////        log.debug("received registry short name:{}", registryShortName);
////
////        List<Route> routes = routeService.findRoutesByRegistryShortName(registryShortName);
////        log.debug("found num of routes:{}", routes.size());
////
////        //TODO: do validation
////        //TODO: think about how to order this....
////        return Converter.toRouteRepList(routes);
////    }
////    
////    @RequestMapping(value = "/registries", method = RequestMethod.GET)
////    public @ResponseBody List<RegistryRep> doGet() {
////
////        List<Registry> registries = baseService.findAll(Registry.class);
////      //TODO: do validation
////        return Converter.toRegistryRepList(registries);
////    }
//    @RequestMapping(value = "/{accountId}/events", method = RequestMethod.GET)
//    public @ResponseBody List<EventRep> findEvents(@PathVariable Long accountId, @RequestParam("since") Long sinceId) {
//        // TODO: do validation and what happend to account id is string
//        log.debug("received call from account id:{}", accountId);
//
//        Account account = baseService.find(Account.class, accountId);
//        List<AbstractEvent> events = eventService.findAscTop10EventsSince(account, sinceId);
//        log.debug("found {} events", events.size());
//
//        // TODO: think about how to order this
//        return converter.toEventReps(events);
//    }
//
//    @RequestMapping(value = "/{accountId}/resourceSizes", method = RequestMethod.GET)
//    public @ResponseBody ResourceSizeRep findResourceSizes(@PathVariable Long accountId) {
//        // TODO: do validation and what happend to account id is string
//        log.debug("received call from account id:{}", accountId);
//
//        Account account = baseService.find(Account.class, accountId);
//        
//        int instanceSize =  1;//awsService.findVersionSizes4Resource(account, ResourceType.Ec2_Instance);
//        int securityGroupSize =  1;//awsService.findVersionSizes4Resource(account, ResourceType.Ec2_Security_Group);
//        int snapshotSize =  1;//awsService.findVersionSizes4Resource(account, ResourceType.Ec2_Snapshot);
//        int imageSize =  1;//awsService.findVersionSizes4Resource(account, ResourceType.Ec2_Image);
//        int volumeSize =  1;//awsService.findVersionSizes4Resource(account, ResourceType.Ec2_Volume);
//        
//        return converter.toResourceSizeRep(instanceSize, securityGroupSize, snapshotSize, imageSize, volumeSize);
//    }
//
//    @RequestMapping(value = "/{accountId}/timeline", method = RequestMethod.GET)
//    public void a(HttpServletResponse response) {
//        JSONObject jsonObject = new JSONObject();
//        try {
//            JSONObject event1 = new JSONObject();
//            event1.put("start", "Sat May 20 1961 00:00:00 GMT-0600");
//            event1.put("title", "Bay of Pigs' Invasion");
//            event1.put("durationEvent", false);
//            
//            JSONArray jsonArray = new JSONArray();
//            jsonArray.put(event1);
//            
//            jsonObject.put("wiki-url", "http://simile.mit.edu/shelf/");
//            jsonObject.put("wiki-section", "Simile JFK Timeline");
//            jsonObject.put("dateTimeFormat", "Gregorian");
//            jsonObject.put("events", jsonArray);
//        } catch (JSONException e) {
//            throw new CloudVersionException("json converter exception");
//        }
//        String jsonString = jsonObject.toString();
//        
//        HttpServletResponseWrapper wrapper = new HttpServletResponseWrapper(response);
//        wrapper.setContentType("application/json;charset=UTF-8");
//        wrapper.setHeader("Content-length", String.valueOf(jsonString.getBytes().length));
//        try {
//            response.getWriter().print(jsonString);
//        } catch (IOException e1) {
//            throw new CloudVersionException("json converter exception");
//        }
//    }
//}