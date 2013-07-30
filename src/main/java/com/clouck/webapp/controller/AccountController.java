package com.clouck.webapp.controller;
//package com.fleeio.webapp.controller;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//
//import com.fleeio.model.Account;
//
//@Controller
//@RequestMapping("/account")
//public class AccountController extends AbstractController {
//    private static final Logger log = LoggerFactory.getLogger(AccountController.class);
//
//    @RequestMapping(method = RequestMethod.GET)
//    public String newAccount(Model model) {
//        log.info("loading add job page");
//
//        Account account = new Account();
//        account.setDisplayName("dr");
//        account.setAccessKeyId("AKIAI77ZHN6ZIB5HQWHA");
//        account.setSecretAccessKey("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");
//        account.setAccountNumber("199588739461");
//
//        model.addAttribute("account", account);
//        return "account";
//    }
//}
