package com.clouck.webapp.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.clouck.converter.RepConverter;
import com.clouck.model.Event;
import com.clouck.service.EventService;

@Controller
@RequestMapping("/events/accounts/{accountId}")
public class EventsController extends AbstractController {
    private static final Logger log = LoggerFactory.getLogger(EventsController.class);
    @Autowired
    private EventService eventService;
    @Autowired
    private RepConverter converter;

//    @RequestMapping(method = RequestMethod.GET)
//    public String load(@PathVariable String accountId, Model model) {
//        log.debug("load events page for account:{}", accountId);
//
//        prep(accountId, model);
//
//        List<Event> events = eventService.findEvents(accountId, 100);
//        model.addAttribute("events", converter.toEventReps(events));
//        return "events";
//    }
}
