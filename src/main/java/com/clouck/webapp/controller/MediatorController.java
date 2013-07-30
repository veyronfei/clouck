package com.clouck.webapp.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.clouck.exception.CloudVersionIllegalStateException;
import com.clouck.model.Account;
import com.clouck.model.User;
import com.google.common.base.Optional;

@Controller
@RequestMapping("/accounts/ec2")
public class MediatorController extends AbstractController {
    private static final Logger log = LoggerFactory.getLogger(MediatorController.class);

    //TODO: It's better to combine this with login controller, as this is similar with overview in ec2 controller
    @RequestMapping(value = "/overview", method = RequestMethod.GET)
    public String loadInitOverview() {
        log.debug("load init overview");
        User user = findCurrentUser();
        String accountId = null;
        int size = user.getAccountIds().size();
        if (size == 0) {
            Optional<Account> oAccount = accountService.findDemoAccount();
            if (oAccount.isPresent()) {
                accountId = oAccount.get().getId();
            } else {
                throw new CloudVersionIllegalStateException("demo account is not loaded.");
            }
        } else {
            accountId = user.getAccountIds().get(0);
        }
        return "redirect:/app/accounts/" + accountId + "/ec2/overview";
    }
}
