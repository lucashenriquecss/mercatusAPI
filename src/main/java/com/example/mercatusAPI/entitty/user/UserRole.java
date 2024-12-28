package com.example.mercatusAPI.entitty.user;

public enum UserRole {
    ADMIN("admin"),
    COMMON("common");

    private String role;

    UserRole(String role){
        this.role = role;
    }

    public String getRole(){
        return role;
    }
}