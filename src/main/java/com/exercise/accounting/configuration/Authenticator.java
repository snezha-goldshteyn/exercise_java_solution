package com.exercise.accounting.configuration;

import com.exercise.accounting.service.AccountingManagement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Configuration
public class Authenticator implements UserDetailsService {

    @Autowired
    private AccountingManagement accounting;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        String password = accounting.getPasswordHash(username);
        if(password == null) {
            throw new UsernameNotFoundException("");
        }
        String[] roles = accounting.getRoles(username).stream().map(r -> "ROLE_"+r).toArray(String[]::new);
        return new User(username, password, AuthorityUtils.createAuthorityList(roles));
    }
}
