package com.clouck.validator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ValidAccessKeyValidator.class)
public @interface ValidAccessKey {

    String message() default "{javax.validation.constraints.ValidAccessKey.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}