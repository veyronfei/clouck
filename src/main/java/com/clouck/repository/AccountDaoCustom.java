package com.clouck.repository;

import java.util.List;

import com.clouck.model.Account;
import com.google.common.base.Optional;

public interface AccountDaoCustom {

    List<Account> findNonDemoAccounts();

    Optional<Account> findDemoAccount();
}
