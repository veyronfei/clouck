package com.clouck.webapp.controller;
//package com.fleeio.webapp.controller;
//
//import java.util.HashMap;
//import java.util.Map;
//
//import javax.validation.Valid;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.servlet.ModelAndView;
//
//import com.fleeio.model.Account;
//import com.fleeio.model.Region;
////import com.fleeio.model.User;
//import com.fleeio.model.aws.ec2.Ec2InstanceVersionList;
//import com.fleeio.service.AwsService;
//import com.fleeio.webapp.form.AddAccountForm;
//
//@Controller
//@RequestMapping("/addaccount")
//public class AddAccountController extends AbstractController {
//    private static final Logger log = LoggerFactory.getLogger(AddAccountController.class);
//    
//    @Autowired
//    private AwsService aws;
//
//    @RequestMapping(method = RequestMethod.GET)
//    public ModelAndView loadPage() {
//        log.info("loading the page");
//        Map<String, Object> map = new HashMap<String, Object>();
//        AddAccountForm f = new AddAccountForm();
//        f.setAccessKey("AKIAI77ZHN6ZIB5HQWHA");
//        f.setSecretKey("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");
//        f.setDstAccessKey("AKIAIMQ7QIDVW7U6VWKQ");
//        f.setDstSecretKey("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");
//        
//        map.put("addAccountForm", f);
//        return new ModelAndView("addAccount", map);
//    }
//
//    @RequestMapping(method = RequestMethod.POST)
//    public ModelAndView post(@ModelAttribute() @Valid AddAccountForm addAccountForm) {
//        log.info("add account...");
//        Account srcAccount = new Account();
//        srcAccount.setAccessKeyId(addAccountForm.getAccessKey());
//        srcAccount.setSecretAccessKey(addAccountForm.getSecretKey());
//        
//        
//        Account dstAccount = new Account();
//        dstAccount.setAccessKeyId(addAccountForm.getDstAccessKey());
//        dstAccount.setSecretAccessKey(addAccountForm.getDstSecretKey());
//        
//        
////        User user = new User();
//        
////        baseService.save(user);
//        
////        AwsVersion av = aws.saveOneVersion(user, Region.Virginia);
////        aws.spinUp(av.get_id());
////        aws.spinDown(av.get_id());
//        
//        //find something we don't support and tell user if they want to proceed.
//
//        return new ModelAndView("redirect:nextPage");
//    }
//}
