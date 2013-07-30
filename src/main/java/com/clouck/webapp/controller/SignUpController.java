package com.clouck.webapp.controller;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.clouck.service.UserService;
import com.clouck.webapp.form.SignupForm;

@Controller
@RequestMapping("/signUp")
public class SignUpController extends AbstractController {
    private static final Logger log = LoggerFactory.getLogger(SignUpController.class);

    @Autowired
    private UserService userService;

    @RequestMapping(method = RequestMethod.GET)
    public String load(Model model) {
        log.debug("access sign up controller");
        SignupForm signupForm = new SignupForm();
        model.addAttribute("signupForm", signupForm);
        return "sign-up";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String signup(@Valid SignupForm signupForm, BindingResult result) {
        log.debug("fullName: {}, email:{}", signupForm.getFullName(), signupForm.getEmail());
        if (result.hasErrors()) {
            return "sign-up";
        } else {
            userService.createUser(signupForm.getFullName(), signupForm.getEmail(), signupForm.getPassword());
            return "redirect:/app/accounts/ec2/overview";
        }
    }
}
