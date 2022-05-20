package com.bokchoywarrior.api.services;

import com.bokchoywarrior.api.models.Account;
import com.bokchoywarrior.api.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class AuthenticationService implements UserDetailsService {

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public User loadUserByUsername(String userName) throws UsernameNotFoundException {
        Account account = accountRepository.findOneByUsername(userName);
        if(account == null){
            throw new UsernameNotFoundException("Username not found : " + userName);
        }

        return createUser(account);
    }

    private User createUser(Account account) {
        return new User(account.getUsername(), account.getPassword(), (List<GrantedAuthority>) createAuthorities(account));
    }

    private Collection<GrantedAuthority> createAuthorities(Account account) {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_" + account.getRole()));
        return authorities;
    }
}