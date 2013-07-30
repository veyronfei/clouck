package com.clouck.service;
//package com.fleeio.service;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.mail.SimpleMailMessage;
//import org.springframework.stereotype.Service;
//
//import com.fleeio.util.EmailUtil;
//
//@Service
//public class EmailServiceImpl extends BaseServiceImpl implements EmailService {
//    private static final Logger log = LoggerFactory.getLogger(EmailServiceImpl.class);
//
//    @Autowired
//    private EmailUtil emailUtil;
//    
//    @Autowired
//    private UserService userService;
//    
//    @Autowired
//    private SimpleMailMessage templateMessage;
//
////    @Override
////    public void sendTaskResult(AbstractTask task) {
////        log.debug("start invoking method sendjobresult with taskId:{}", task.getId());
////        
////        SpringSecurityUser user = userService.findUserByTask(task);
////        templateMessage.setTo(user.getEmail());
////
////        Map<String, Serializable> model = new HashMap<String, Serializable>();
////        model.put("user", user);
////        
////        List<TaskResult> taskResults = task.getResults();
////        log.debug("find {} task results for task id:{}", taskResults.size(), task.getId());
////
////        TaskTemplate tt = TaskTemplate.findTaskTemplate(task);
////        model.put(tt.findEmailTemplateVariableName(), (Serializable) taskResults);
////
////        log.info("start sending task results email for user:{} with email:{}", user.getFullName(), user.getEmail());
////        emailUtil.send(templateMessage, EmailTemplate.Task_Result, model);
////    }
//}
