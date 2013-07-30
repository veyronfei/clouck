package com.clouck.model;

import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

@TypeAlias(value = "account")
@Document(collection = "account")
@SuppressWarnings("serial")
public class Account extends AbstractModel {
    public enum AccountType {
        Demo, Admin, Normal
    }
    private String name;
	private String accessKeyId;
	private String secretAccessKey;
    private String accountNumber;
    private AccountType accountType;

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

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
