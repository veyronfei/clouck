package com.clouck.repository;

import org.springframework.data.repository.CrudRepository;

import com.clouck.model.Account;

public interface AccountDao extends CrudRepository<Account, String>, AccountDaoCustom {
}
