package com.clouck.webapp.controller;
//package com.fleeio.webapp.controller;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//
//import com.fleeio.model.AlertConf;
//import com.fleeio.model.EventType;
//import com.fleeio.model.SpringSecurityUser;
//import com.fleeio.service.UserService;
//
//@Controller
//@RequestMapping("/alerts")
//public class AlertsController extends AbstractController {
//    private static final Logger log = LoggerFactory.getLogger(AlertsController.class);
//
//    @Autowired
//    private UserService userService;
//
//    @RequestMapping(method = RequestMethod.GET)
//    public String load(Model model) {
//        log.debug("loading alerts page, get");
//
//        SpringSecurityUser user = findCurrentAccount();
//        model.addAttribute("user", user);
//        loadList(model);
//        return "alerts";
//    }
//
//    @RequestMapping(method = RequestMethod.POST)
//    public String post(@ModelAttribute("user") SpringSecurityUser user) {
//        log.debug("loading alerts page, post");
//
//        userService.updateUser(user);
//        return "alerts";
//    }
//
//    @RequestMapping(value = "/{type}", method = RequestMethod.GET)
//    public String loadWithType(@PathVariable String type, Model model) {
//        log.info("loading alerts page with param:{}", type);
//        // TODO: error handling
//        EventType eventType = EventType.valueOf(type);
//
//        SpringSecurityUser user = findCurrentAccount();
//
//        AlertConf alertConf = new AlertConf();
//        alertConf.setEventType(eventType);
//
//        user.addAlertConf(alertConf);
//        userService.updateUser(user);
//
//        model.addAttribute("user", user);
//        return "redirect:/app/alerts";
//    }
//
//    private void loadList(Model model) {
//        model.addAttribute("eventTypes", EventType.values());
//    }
//}
