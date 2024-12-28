package com.example.mercatusAPI.dto.auth;

import com.example.mercatusAPI.entitty.user.UserRole;

public record RegisterDTO(String email, String password, UserRole role, String name) {
    
}
