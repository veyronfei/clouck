package com.clouck.service;

import com.clouck.model.User;
import com.google.common.base.Optional;
//import com.fleeio.model.SpringSecurityUser;

public interface UserService {

    User save(User user);

    User createUser(String fullName, String email, String password);

    Optional<User> findByEmail(String email);
//
//    SpringSecurityUser updateUser(SpringSecurityUser user);
//
//    void addNewAccount(Account account, SpringSecurityUser user);
//
//    void updateAccount(Account account);
//
//    void deleteAccount(Account account);

//    List<Account> findAllNonDemoAccounts();

//    Account createNewAccount();
}
