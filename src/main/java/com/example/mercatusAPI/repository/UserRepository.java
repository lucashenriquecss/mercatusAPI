package com.example.mercatusAPI.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import com.example.mercatusAPI.entitty.user.User;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
        UserDetails findByEmail(String email);
}
