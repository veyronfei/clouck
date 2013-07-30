package com.clouck.webapp.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/logout")
public class LogoutController extends AbstractController {
    private static final Logger log = LoggerFactory.getLogger(LogoutController.class);

    @Autowired
    private UserPreference userPreference;
    
    @RequestMapping(method = RequestMethod.GET)
    public String load(Model model) {
        log.debug("logout");
        return "redirect:login";
    }
}
