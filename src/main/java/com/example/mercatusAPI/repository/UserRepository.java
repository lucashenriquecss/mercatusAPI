package com.example.mercatusAPI.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.mercatusAPI.entitty.user.User;

public interface UserRepository extends JpaRepository<User, String> {
        UserDetails findByEmail(String email);
}
