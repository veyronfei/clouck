package com.clouck.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.clouck.model.Role;
import com.clouck.model.User;
import com.clouck.repository.UserRepository;
import com.google.common.base.Optional;
//import com.fleeio.model.SpringSecurityAuthority;
//import com.fleeio.model.SpringSecurityAuthority.SpringSecurityUserRole;
//import com.fleeio.model.SpringSecurityUser;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userDao;

    @Override
    public User createUser(String fullName, String email, String password) {
        User user = new User();
        user.setFullName(fullName);
        user.setEmail(email.toLowerCase());
        user.setPassword(password);
        user.getRoles().add(Role.ROLE_USER);
        //TODO Should I check last error?
        return save(user);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        User user = userDao.findByEmail(email);
        if (user == null) {
            return Optional.absent();
        } else {
            return Optional.of(user);
        }
    }

    @Override
    public User save(User user) {
        return userDao.save(user);
    }
//
//    @Override
//    public List<Account> findAllNonDemoAccounts() {
//        return userDao.findAllNonDemoAccounts();
//    }
//
//    @Override
//    public SpringSecurityUser updateUser(SpringSecurityUser user) {
//        return userDao.updateUser(user);
//    }
//    
//    @Override
//    public void addNewAccount(Account account, SpringSecurityUser user) {
//        user.addAccount(account);
//        updateUser(user);
//    }
//
//    @Override
//    public void updateAccount(Account account) {
//        merge(account);
//    }
//
//    @Override
//    public void deleteAccount(Account account) {
//        remove(account);
//    }
//
//    @Override
//    public Account createNewAccount() {
//        Account newAccount = new Account();
//        newAccount.setDisplayName("dr");
//        newAccount.setAccessKeyId("AKIAI77ZHN6ZIB5HQWHA");
//        newAccount.setSecretAccessKey("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");
//        newAccount.setAccountNumber("199588739461");
//        return newAccount;
//    }
}