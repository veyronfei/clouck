package com.clouck.rest;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.clouck.exception.CloudVersionException;
import com.clouck.model.User;
import com.clouck.service.UserService;
import com.clouck.webapp.controller.AbstractController;

@Controller
@RequestMapping(value = "/rest/accounts")
public class AccountsRestController extends AbstractController {
    private static final Logger log = LoggerFactory.getLogger(AccountsRestController.class);

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/{accountId}", method = RequestMethod.DELETE)
    public void delete(@PathVariable String accountId) {
        log.debug("received call from account id:{}", accountId);
        User currentUser = findCurrentUser();
        List<String> accountIds = currentUser.getAccountIds();
        if (accountIds.contains(accountId)) {
            currentUser.getAccountIds().remove(accountId);
            userService.save(currentUser);
            accountService.delete(accountId);
        } else {
            throw new CloudVersionException("invalid request, user:" + currentUser.getEmail() +
                    " don't have authority to access account id:" + accountId);
        }
    }
}
