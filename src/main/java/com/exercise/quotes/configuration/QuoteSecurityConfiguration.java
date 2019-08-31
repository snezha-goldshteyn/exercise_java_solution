package com.exercise.quotes.configuration;

import com.exercise.accounting.configuration.Authenticator;
import com.exercise.accounting.service.AccountingManagement;
import com.exercise.accounting.service.AccountingManagementMongoImpl;
import com.exercise.quotes.controllers.ApiConstants;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.bind.PropertySourcesPlaceholdersResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableMongoRepositories("com.exercise.accounting.repo")
public class QuoteSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Value("${authentication:enabled}")
    String authentication;

    @Value("${authorization:disabled}")
    String authorization;

    @Bean
    Authenticator getAuthenticator() {
        return new Authenticator();
    }

    @Bean
    PasswordEncoder getEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic();
        http.csrf().disable();
        if (authentication.equals("disabled")) {
            http.authorizeRequests().anyRequest().permitAll();
            return;
        }
        if (authorization.equals("disabled")) {
            http.authorizeRequests().anyRequest().authenticated();
        }

    }
}
