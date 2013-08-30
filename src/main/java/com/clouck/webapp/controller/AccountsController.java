package com.clouck.webapp.controller;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.clouck.model.Account;
import com.clouck.model.Region;
import com.clouck.model.User;
import com.clouck.service.AwsService;
import com.clouck.service.ResourceService;
import com.clouck.service.UserService;
import com.clouck.webapp.form.AccountForm;

@Controller
@RequestMapping("/accounts")
public class AccountsController extends AbstractController {
    private static final Logger log = LoggerFactory.getLogger(AccountsController.class);
    
    @Autowired
    private UserService userService;
    @Autowired
    private AwsService awsService;
    @Autowired
    private UserPreference userPreference;
    @Autowired
    private ResourceService resourceService;

//    @RequestMapping(value = "/{accountId}", method = RequestMethod.GET)
//    public String loadAccount(Model model, @PathVariable String accountId) {
//        log.debug("edit account id:{}", accountId);
//
//        User user = findCurrentUser();
//        model.addAttribute("currentUser", user);
//        AccountForm newAccount = new AccountForm();
//        model.addAttribute("newAccount", newAccount);
//        Account account = accountService.findAccount(accountId);
//        model.addAttribute("account", account);
//
//        return "account";
//    }

    @RequestMapping(method = RequestMethod.GET)
    public String load(Model model) {
        log.debug("loading setting page");

        User user = findCurrentUser();
        model.addAttribute("currentUser", user);
        AccountForm newAccount = new AccountForm();
        model.addAttribute("newAccount", newAccount);
        List<Account> accounts = accountService.find(user.getAccountIds());
        model.addAttribute("accounts", accounts);
        return "accounts";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String addAccount(@ModelAttribute("newAccount") @Valid AccountForm accountForm, BindingResult result) {
        if (result.hasErrors()) {
            return "ec2-overview";
        }
        log.info("add a new account");
        //TODO: check if credential is correct
        User currentUser = findCurrentUser();
        String accountNumber = awsService.findUserId(accountForm.getAccessKeyId(), accountForm.getSecretAccessKey());
        Account newAccount = accountService.createAccount(accountForm.getName(), accountForm.getAccessKeyId(),
                accountForm.getSecretAccessKey(), accountNumber);
        currentUser.getAccountIds().add(newAccount.getId());
        userService.save(currentUser);
        resourceService.scanAccount(newAccount);
        return "redirect:/accounts/" + newAccount.getId() + "/ec2/overview";
    }
}
