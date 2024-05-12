package com.example.shopping.domain;

public enum RoleType {
    ROLE_USER("ROLE_USER"),
    ROLE_SELLER("ROLE_SELLER"),
    ROLE_ADMIN("ROLE_ADMIN");

    String role;

    RoleType(String role) {
        this.role = role;
    }

    public String value() {
        return role;
    }
}
