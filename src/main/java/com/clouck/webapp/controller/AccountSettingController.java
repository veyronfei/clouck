//package com.clouck.webapp.controller;
//
//import java.util.List;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//
//import com.clouck.model.Account;
//import com.clouck.model.User;
//import com.clouck.service.AccountService;
//import com.clouck.webapp.form.AccountForm;
//
//@Controller
//@RequestMapping("/accountSetting")
//public class AccountSettingController extends AbstractController {
//    private static final Logger log = LoggerFactory.getLogger(AccountSettingController.class);
//    @Autowired
//    private AccountService accountService;
//
//    @RequestMapping(method = RequestMethod.GET)
//    public String load(Model model) {
//        log.debug("loading setting page");
//
//        User user = findCurrentUser();
//        model.addAttribute("currentUser", user);
//        AccountForm newAccount = new AccountForm();
//        model.addAttribute("newAccount", newAccount);
//        List<Account> accounts = accountService.find(user.getAccountIds());
//        model.addAttribute("accounts", accounts);
//        return "account-setting";
//    }
//}
