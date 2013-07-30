package com.clouck.webapp.form;

import org.hibernate.validator.constraints.NotEmpty;

import com.clouck.validator.ValidAccessKey;

@ValidAccessKey
public class AccountForm {

    @NotEmpty(message = "name is required")
    private String name;

    @NotEmpty(message = "access key id is required")
    private String accessKeyId;

    @NotEmpty(message = "secret access key id is required")
    private String secretAccessKey;

    public String getAccessKeyId() {
        return accessKeyId;
    }

    public void setAccessKeyId(String accessKeyId) {
        this.accessKeyId = accessKeyId;
    }

    public String getSecretAccessKey() {
        return secretAccessKey;
    }

    public void setSecretAccessKey(String secretAccessKey) {
        this.secretAccessKey = secretAccessKey;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
