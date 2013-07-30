package com.clouck.repository;

import static org.springframework.data.mongodb.core.query.Criteria.where;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Query;

import com.clouck.model.Account;
import com.clouck.model.Account.AccountType;
import com.google.common.base.Optional;

public class AccountDaoImpl implements AccountDaoCustom {
    private static final Logger log = LoggerFactory.getLogger(AwsRepositoryImpl.class);

    @Autowired
    private MongoOperations mongoOps;

    @Override
    public List<Account> findNonDemoAccounts() {
        return mongoOps.find(new Query(where("accountType").ne(AccountType.Demo)), Account.class);
    }

    @Override
    public Optional<Account> findDemoAccount() {
        Account result = mongoOps.findOne(new Query(where("accountType").is(AccountType.Demo)), Account.class);
        if (result != null) {
            return Optional.of(result);
        } else {
            return Optional.absent();
        }
    }
}