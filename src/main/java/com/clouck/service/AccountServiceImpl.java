package com.clouck.service;

import java.util.Collection;
import java.util.List;

import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.clouck.model.Account;
import com.clouck.repository.AccountDao;
import com.google.common.base.Optional;
import com.google.common.collect.Lists;

@Service
public class AccountServiceImpl implements AccountService {
    private static final Logger log = LoggerFactory.getLogger(AccountServiceImpl.class);

    @Autowired
    private AwsService awsService;

    @Autowired
    private AccountDao accountDao;

    @Override
    public List<Account> findNonDemoAccounts() {
        return accountDao.findNonDemoAccounts();
    }

    @Override
    public Optional<Account> findDemoAccount() {
        return accountDao.findDemoAccount();
    }

    @Override
    public Optional<Account> find(String accountId) {
        Validate.notNull(accountId);
        Account account = accountDao.findOne(accountId);
        if (account == null) {
            return Optional.absent();
        } else {
            return Optional.of(account);
        }
    }

    @Override
    public Account findAccount(String accountId) {
        Validate.notNull(accountId);
        Account account = accountDao.findOne(accountId);
        if (account == null) {
            throw new IllegalArgumentException(String.format("invalid account id:%s", accountId));
        } else {
            return account;
        }
    }

    @Override
    public List<Account> find(Collection<String> accountIds) {
        return Lists.newArrayList(accountDao.findAll(accountIds));
    }

    @Override
    public void delete(String accountId) {
        accountDao.delete(accountId);
    }

    @Override
    public Account createAccount(String fullName, String accessKeyId, String secretAccessKey, String accountNumber) {
        Validate.noNullElements(new Object[]{fullName, accessKeyId, secretAccessKey, accountNumber});
        Account account = new Account();
        account.setName(fullName);
        account.setAccessKeyId(accessKeyId);
        account.setSecretAccessKey(secretAccessKey);
        account.setAccountNumber(accountNumber);
        return accountDao.save(account);
    }
}
