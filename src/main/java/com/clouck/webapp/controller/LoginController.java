package com.clouck.webapp.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.clouck.service.UserService;

@Controller
@RequestMapping("/")
public class LoginController extends AbstractController {
    private static final Logger log = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private UserService userService;
    
    @Autowired
    private UserPreference userPreferences;
    
    @RequestMapping(method = RequestMethod.GET)
    public String load(Model model) {
        log.debug("access login controller");
//        LoginForm loginForm = new LoginForm();
//        model.addAttribute("loginForm", loginForm);
        return "login";
    }

//    @RequestMapping(method = RequestMethod.POST)
//    public String login(@Valid LoginForm loginForm, BindingResult result) {
//        log.debug("email:{}", loginForm.getEmail());
//        if (result.hasErrors()) {
//            return "login";
//        }
//
//        Optional<User> oUser = userService.findByEmail(loginForm.getEmail());
//        if (oUser.isPresent()) {
//            if (oUser.get().getPassword().equals(loginForm.getPassword())) {
//                userPreferences.setCurrentUser(oUser.get());
//                return "redirect:/app/overview";
//            }
//        }
//        //TODO: HOW TO DISPLAY error.
//        return "login";
//    }
}
