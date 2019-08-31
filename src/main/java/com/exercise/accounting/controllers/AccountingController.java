package com.exercise.accounting.controllers;

import com.exercise.accounting.api.*;
import com.exercise.accounting.service.AccountingManagement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static com.exercise.accounting.api.AccountingApiConstants.*;

@RestController
public class AccountingController {
    @Autowired
    AccountingManagement accounting;

    @PostMapping(value=AccountingApiConstants.ADD_ACCOUNT)
    AccountingCodes addAccount(@RequestBody AccountDto account) {
        return accounting.addAccount(account);
    }

    @DeleteMapping(value=DELETE_ACCOUNT)
    AccountingCodes removeAccount
            (@RequestParam(name=USERNAME) String username) {
        return accounting.removeAccount(username);
    }

    @PostMapping(value=ADD_ROLE)
    AccountingCodes addRole(@RequestBody UsernameRoleDto roleData) {
        return accounting.addRole(roleData.getUsername(), roleData.getRole());
    }
    @PostMapping(value=REMOVE_ROLE)
    AccountingCodes removeRole
            (@RequestBody UsernameRoleDto roleData) {
        return accounting.removeRole(roleData.getUsername(), roleData.getRole());
    }
}
