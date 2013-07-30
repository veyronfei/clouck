package com.clouck.scheduler;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.clouck.model.Account;
import com.clouck.service.AccountService;
import com.clouck.service.EventService;
import com.clouck.service.ResourceService;

@Component
public class CentralScheduler {
    private static final Logger log = LoggerFactory.getLogger(CentralScheduler.class);

    @Autowired
    private AccountService accountService;
    
    @Autowired
    private ResourceService resourceService;
    
    @Autowired
    private EventService eventService;

    @Scheduled(fixedDelay = 10000)
    public void scanNonDemoAccounts() {
        log.debug("================scan all accounts================");

        List<Account> accounts = accountService.findNonDemoAccounts();
        log.debug("found {} non demo accounts", accounts.size());
        for (Account account : accounts) {
            resourceService.scanAccount(account);
        }
        log.debug("================end scan all accounts================");
    }

    @Scheduled(fixedDelay = 5000)
    public void convertEc2Reservation2Ec2Instance() {
        log.debug("================process all accounts raw resources================");

        List<Account> accounts = accountService.findNonDemoAccounts();
        log.debug("found {} accounts", accounts.size());
        for (Account account : accounts) {
            resourceService.convertEc2Reservation2Ec2Instance(account);
        }
        log.debug("================process all accounts raw resources================");
    }

    @Scheduled(fixedDelay = 5000)
    public void generateEvents() {
        log.debug("================generate events for all accounts================");

        List<Account> accounts = accountService.findNonDemoAccounts();
        log.debug("found {} accounts", accounts.size());
        for (Account account : accounts) {
            eventService.generateEvents(account);
        }
        log.debug("================end generate events for all accounts================");
    }
}
