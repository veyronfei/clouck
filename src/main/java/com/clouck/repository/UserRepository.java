package com.clouck.repository;

import org.springframework.data.repository.CrudRepository;

import com.clouck.model.User;

//import com.fleeio.model.SpringSecurityUser;

public interface UserRepository extends CrudRepository<User, String> {

    User findByEmail(String email);
}

// void persist(Serializable entity);
//
// SpringSecurityUser findByEmail(String email);
//
// SpringSecurityUser updateUser(SpringSecurityUser user);

// List<Account> findAllNonDemoAccounts();