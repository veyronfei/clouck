package com.clouck.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.clouck.model.Account;
import com.clouck.model.Account.AccountType;
import com.clouck.repository.AccountDao;
import com.clouck.repository.UserRepository;
import com.google.common.base.Optional;

@Service
public class DataLoadingServiceImpl implements DataLoadingService {
    private static final Logger log = LoggerFactory.getLogger(DataLoadingServiceImpl.class);

    @Autowired
    private AccountDao accountDao;
    
    @Autowired
    private UserRepository userDao;

    @Override
    public void loadDemoData() {
        Optional<Account> oAccount = accountDao.findDemoAccount();
        //demo, default data has not loaded.
        if (!oAccount.isPresent()) {
            loadDemoAccount();
        }
    }

    private Account loadDemoAccount() {
        Account account = new Account();
        account.setAccountType(AccountType.Demo);
        account.setAccessKeyId("xxxx");
        account.setSecretAccessKey("xxxx");
        account.setName("Demo Account");
        account.setAccountNumber("109553729261");
        return accountDao.save(account);
    }
}