package com.exercise.accounting.service;

import com.exercise.accounting.api.AccountDto;
import com.exercise.accounting.api.AccountingCodes;

import java.time.LocalDateTime;
import java.util.HashSet;

public interface AccountingManagement {
    AccountingCodes addAccount(AccountDto account);
    AccountingCodes removeAccount(String username);
    String getPasswordHash(String username);
    HashSet<String> getRoles(String username);
    AccountingCodes  addRole(String username, String role);
    AccountingCodes removeRole(String username, String role);

}
