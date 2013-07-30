package com.clouck.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.clouck.model.User;
import com.clouck.service.UserService;
import com.google.common.base.Optional;

public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String> {

    @Autowired
    private UserService userService;

    @Override
    public void initialize(UniqueEmail constraintAnnotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        Optional<User> oUser = userService.findByEmail(value);
        return oUser.isPresent() ? false : true;
    }
}