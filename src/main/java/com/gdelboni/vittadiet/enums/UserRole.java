package com.gdelboni.vittadiet.enums;

public enum UserRole {
    USER("USER"),
    ADMIN("ADMIN");

    private final String role;

    UserRole(String role) {
        this.role = role;
    }

    public String toRoleSecurity(){
        return "ROLE_" + this.role;
    }

    private String getRole() {
        return role;
    }
}
