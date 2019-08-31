package com.exercise.accounting.api;

import lombok.Data;

@Data
public class UsernamePasswordDto {
    private String username;
    private String password;

    public UsernamePasswordDto(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public UsernamePasswordDto() {
    }
}
