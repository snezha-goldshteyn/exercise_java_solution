package com.exercise.accounting.api;

import lombok.Data;

import java.util.HashSet;

@Data
public class AccountDto {
    private String username;
    private String password;
    private HashSet<String> roles;

    public AccountDto(String username, String password, HashSet<String> roles) {
        super();
        this.username = username;
        this.password = password;
        this.roles = roles;
    }

    public AccountDto() {
    }
}
