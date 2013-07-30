package com.clouck.service;

import java.util.Collection;
import java.util.List;

import com.clouck.model.Account;
import com.google.common.base.Optional;

public interface AccountService {

    List<Account> findNonDemoAccounts();

    Optional<Account> find(String accountId);

    List<Account> find(Collection<String> accountIds);

    Optional<Account> findDemoAccount();

    Account createAccount(String fullName, String accessKeyId, String secretAccessKey, String accountNumber);

    Account findAccount(String accountId);

    void delete(String accountId);
}
