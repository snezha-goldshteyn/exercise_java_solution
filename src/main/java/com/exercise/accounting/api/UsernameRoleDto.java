package com.exercise.accounting.api;

import lombok.Data;

@Data
public class UsernameRoleDto {
    private String username;
    private String role;

    public UsernameRoleDto(String username, String role) {
        this.username = username;
        this.role = role;
    }

    public UsernameRoleDto() {
    }
}
