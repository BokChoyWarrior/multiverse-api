package com.bokchoywarrior.api.services;

import com.bokchoywarrior.api.models.Account;
import com.bokchoywarrior.api.models.enums.AccountRole;
import com.bokchoywarrior.api.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @PostConstruct
    public void initialize(){
        if(accountRepository.findOneByUsername("admin") == null){
            save(new Account("admin", "admin", AccountRole.WRITE.name()));
        }
        if(accountRepository.findOneByUsername("user") == null){
            save(new Account("user", "user", AccountRole.READ.name()));
        }
    }

    @Transactional
    private Account save(Account user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return accountRepository.save(user);
    }

    private boolean usernameExists(final String username) {
        return accountRepository.findOneByUsername(username) != null;
    }
}