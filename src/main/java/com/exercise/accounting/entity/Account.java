package com.exercise.accounting.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.LinkedList;


@Document(collection = "accounts")
public class Account {
    @Id
    String username;
    String hashCode;
    public HashSet<String> roles = new HashSet<>();

    public Account() {
    }

    public Account(String username, String hashCode, LocalDateTime activationDate) {
        this.username = username;
        this.hashCode = hashCode;
    }

    public HashSet<String> getRoles() {
        return roles;
    }

    public void setRoles(HashSet<String> roles) {
        this.roles = roles;
    }

    public String getHashCode() {
        return hashCode;
    }

    public void setHashCode(String hashCode) {
        this.hashCode = hashCode;
    }

}
