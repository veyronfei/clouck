package com.clouck.webapp.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;

import com.clouck.application.SystemCache;
import com.clouck.exception.CloudVersionException;
import com.clouck.exception.CloudVersionIllegalStateException;
import com.clouck.model.Region;
import com.clouck.model.User;
import com.clouck.model.aws.AbstractResource;
import com.clouck.service.AccountService;
import com.clouck.webapp.form.AccountForm;
import com.google.common.base.Optional;

public abstract class AbstractController {
    private static final Logger log = LoggerFactory.getLogger(AbstractController.class);

    @Autowired
    protected AccountService accountService;

    @Autowired
    private SystemCache systemCache;

    protected void prep(String currentAccountId, String regionEndpoint, Model model) {
        User currentUser = findCurrentUser();
        List<String> accountIds = currentUser.getAccountIds();
        //prevent user change id to steal other users information, need to change lines below as well
        if (currentAccountId.equals(systemCache.findDemoAccountId()) || accountIds.contains(currentAccountId)) {
            AccountForm newAccount = new AccountForm();
            model.addAttribute("newAccount", newAccount);
            model.addAttribute("currentUser", currentUser);
            Optional<Region> oRegion = Region.toRegion(regionEndpoint);
            if (oRegion.isPresent()) {
                model.addAttribute("currentRegion", oRegion.get());
            } else {
                throw new CloudVersionIllegalStateException(regionEndpoint + "is not a valid region end point.");
            }
            model.addAttribute("regions", Region.values());
            model.addAttribute("currentAccount", accountService.find(currentAccountId).get());
            model.addAttribute("demoAccountId", systemCache.findDemoAccountId());
            List<String> cloneAccountIds = new ArrayList<>(accountIds);
            cloneAccountIds.remove(currentAccountId);
            model.addAttribute("accounts", accountService.find(cloneAccountIds));
            model.addAttribute("millis", DateTime.now().toInstant().getMillis());
        } else {
            throw new CloudVersionException("invalid request, user:" + currentUser.getEmail() +
                    " don't have authority to access account id:" + currentAccountId);
        }
    }

    protected User findCurrentUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof User) {
            return ((User)principal);
        } else {
            throw new CloudVersionIllegalStateException("should be User class, but got:" + principal.getClass().getSimpleName());
        }
    }

    protected Region findRegion(String regionEndpoint) {
        Optional<Region> oRegion = Region.toRegion(regionEndpoint);
        if (oRegion.isPresent()) {
            return oRegion.get();
        } else {
            throw new CloudVersionIllegalStateException("invalid region end point:" + regionEndpoint);
        }
    }
}
