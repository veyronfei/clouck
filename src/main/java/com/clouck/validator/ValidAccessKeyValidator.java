package com.clouck.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.amazonaws.AmazonServiceException;
import com.clouck.service.AwsService;
import com.clouck.webapp.form.AccountForm;

public class ValidAccessKeyValidator implements ConstraintValidator<ValidAccessKey, AccountForm> {
    private static final Logger log = LoggerFactory.getLogger(ValidAccessKeyValidator.class);

    @Autowired
    private AwsService awsService;

    @Override
    public void initialize(ValidAccessKey constraintAnnotation) {
    }

    @Override
    public boolean isValid(AccountForm value, ConstraintValidatorContext context) {
        // its not this valids job to validate empty
        if (value == null || StringUtils.isEmpty(value.getAccessKeyId()) || StringUtils.isEmpty(value.getSecretAccessKey())) {
            return true;
        }
        String accessKeyId = value.getAccessKeyId();
        String secretAccessKey = value.getSecretAccessKey();

        try {
            String accountNumber = awsService.findUserId(accessKeyId, secretAccessKey);
        } catch (AmazonServiceException ex) {
            if (ex.getStatusCode() == 403 && ex.getErrorCode().equals("InvalidClientTokenId")) {
                return false;
            }
        }
        return true;
    }
}