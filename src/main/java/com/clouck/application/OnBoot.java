package com.clouck.application;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.clouck.service.DataLoadingService;

@Component
@Lazy(false)
public class OnBoot {
    private static final Logger log = LoggerFactory.getLogger(OnBoot.class);

    @Autowired
    private DataLoadingService dataLoadingService;
    
    @Autowired
    private SystemCache systemCache;
    
    @Autowired
    private AmqpTemplate template;
//    
//    @Autowired
//    private FleeioValidator validator;

    @PostConstruct
    private void boot() {
        log.debug("boot method in OnBoot class invoked.");
        dataLoadingService.loadDemoData();
        
//        template.convertAndSend("myqueue", "foo");
//        String foo = (String) template.receiveAndConvert("myqueue");
//        
//        System.out.println("===========================================" + foo);
        
        //TODO: load jobs for schedule factory
        //validator.validate();
        //validate all validator have been registered.
        //validation eventconf that should have the same size of resourcetype
        //validate all resource type enum contain all event type
        //nomal account must have user associated with it, except default user.
        //validate for event conf, that accoundid, region, resourcetype is unique
    }
}
