package com.exercise.accounting.configuration;

import com.exercise.quotes.controllers.ApiConstants;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class AccountingSecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Value("${authentication:enabled}")
    private String authentication;
    @Value("${authorization:disabled}")
    private String authorization;

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.httpBasic();
        httpSecurity.csrf().disable();
        if (authentication.equals("disabled")) {
            httpSecurity.authorizeRequests().anyRequest().permitAll();
            return;
        }
        if (authorization.equals("disabled")) {
            httpSecurity.authorizeRequests().anyRequest().authenticated();
            return;
        }
        httpSecurity.authorizeRequests().antMatchers(ApiConstants.GET_QUOTE).authenticated();
        httpSecurity.authorizeRequests().antMatchers(ApiConstants.GET_ALL_QUOTES).authenticated();
        httpSecurity.authorizeRequests().antMatchers(ApiConstants.ADD_QUOTE).hasAnyRole("ADMIN", "USER");
        httpSecurity.authorizeRequests().antMatchers(ApiConstants.UPDATE_QUOTE).hasAnyRole("ADMIN", "USER");
        httpSecurity.authorizeRequests().antMatchers(ApiConstants.DELETE_QUOTE).hasAnyRole("ADMIN", "USER");
    }
}
