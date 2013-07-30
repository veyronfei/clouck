package com.clouck.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.clouck.model.User;
import com.clouck.repository.UserRepository;

@Component
public class MongoDbUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        String email = username.toLowerCase();
        User user = userDao.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("email not found");
        } else {
            return user;
        }
    }
}
