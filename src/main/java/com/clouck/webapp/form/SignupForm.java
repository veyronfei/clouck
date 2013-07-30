package com.clouck.webapp.form;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import com.clouck.validator.UniqueEmail;

public class SignupForm {
    @NotEmpty
    private String fullName;

    @Email
    @NotEmpty
    @UniqueEmail
    private String email;

    @NotEmpty
    private String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}
