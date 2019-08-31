package com.exercise.accounting.service;

import com.exercise.accounting.api.AccountDto;
import com.exercise.accounting.api.AccountingCodes;
import com.exercise.accounting.entity.Account;
import com.exercise.accounting.repo.AccountsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;

@Service
public class AccountingManagementMongoImpl implements AccountingManagement {
    @Autowired
    AccountsRepository repository;

    @Autowired
    PasswordEncoder encoder;

    @Value("${password_length:5}")
    private int passwordLength;

    @Override
    public AccountingCodes addAccount(AccountDto account) {
        if(repository.existsById(account.getUsername())) {
            return AccountingCodes.ACCOUNT_ALREADY_EXISTS;
        }
        if(!isPasswordValid(account.getPassword())) {
            return AccountingCodes.WRONG_PASSWORD;
        }
        Account accountMongo =
                new Account(account.getUsername(),
                        getHashCode(account.getPassword()), LocalDateTime.now());
        if(account.getRoles() != null) {
            accountMongo.setRoles(account.getRoles());
        }
        repository.save(accountMongo);
        return AccountingCodes.OK;
    }

    private String getHashCode(String password) {

        return encoder.encode(password);
    }

    private boolean isPasswordValid(String password) {
        return password.length() >= passwordLength;
    }

    @Override
    public AccountingCodes removeAccount(String username) {
        if (!repository.existsById(username)) return AccountingCodes.ACCOUNT_NOT_EXISTS;
        repository.deleteById(username);
        return AccountingCodes.OK;
    }

    @Override
    public String getPasswordHash(String username) {
        Account accountMongo = NotExists(username);
        if (accountMongo != null) {
            return accountMongo.getHashCode();
        }
        return null;
    }


    private Account NotExists(String username) {
        if (!repository.existsById(username)) {
            return null;
        }
        Account accountMongo = repository.findById(username).get();
        return accountMongo;
    }

    @Override
    public HashSet<String> getRoles(String username) {
        Account accountMongo = NotExists(username);
        if (accountMongo != null)
            return accountMongo.getRoles();
        else return null;
    }

    @Override
    public AccountingCodes addRole(String username, String role) {
        if (!repository.existsById(username))
            return AccountingCodes.ACCOUNT_NOT_EXISTS;
        HashSet<String> roles = getRoles(username);
        if (roles.contains(role))
            return AccountingCodes.ROLE_ALREADY_EXISTS;
        Account accountMongo = repository.findById(username).get();
        roles.add(role);
        accountMongo.setRoles(roles);
        repository.save(accountMongo);
        return AccountingCodes.OK;
    }

    @Override
    public AccountingCodes removeRole(String username, String role) {
        if (!repository.existsById(username))
            return AccountingCodes.ACCOUNT_NOT_EXISTS;

        Account accountMongo = repository.findById(username).get();
        HashSet<String> roles = getRoles(username);
        if (!roles.contains(role))
            return AccountingCodes.ROLE_NOT_EXISTS;
        roles.remove(role);
        accountMongo.setRoles(roles);
        repository.save(accountMongo);
        return AccountingCodes.OK;
    }

}
