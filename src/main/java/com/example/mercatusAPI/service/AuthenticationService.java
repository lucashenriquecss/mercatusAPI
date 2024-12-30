package com.example.mercatusAPI.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;

import com.example.mercatusAPI.repository.InventoryRepository;
import com.example.mercatusAPI.repository.UserRepository;
import com.example.mercatusAPI.dto.auth.AuthenticationDTO;
import com.example.mercatusAPI.dto.auth.LoginResponseDTO;
import com.example.mercatusAPI.dto.auth.RegisterDTO;
import com.example.mercatusAPI.entitty.user.User;
import com.example.mercatusAPI.entitty.inventory.Inventory;

import com.example.mercatusAPI.exception.auth.*;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Service
@RequiredArgsConstructor
public class AuthenticationService implements UserDetailsService {

    
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private InventoryRepository inventoryRepository;
    
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserDetails user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with email: " + email);
        }
        return user;
    }

    public LoginResponseDTO authenticate(AuthenticationDTO credentials) {
        try {
            var authToken = new UsernamePasswordAuthenticationToken(
                credentials.email(), 
                credentials.password()
            );

            var auth = authenticationManager.authenticate(authToken);

            return tokenService.generateToken((User) auth.getPrincipal());
        } catch (BadCredentialsException e) {
            throw new AuthenticationFailedException("Invalid credentials");
        }
    }

    public LoginResponseDTO refreshToken(String refreshToken) {
        try {
            return tokenService.generateRefreshToken(refreshToken);
        } catch (Exception e) {
            throw new InvalidTokenException("Invalid or expired refresh token");
        }
    }

    @Transactional
    public void registerUser(RegisterDTO registrationData) {

        if (userRepository.findByEmail(registrationData.email()) != null) {
            throw new UserAlreadyExistsException("Email already registered");
        }

        String encryptedPassword = new BCryptPasswordEncoder().encode(registrationData.password());

        User newUser = User.builder()
            .email(registrationData.email())
            .password(encryptedPassword)
            .role(registrationData.role())
            .name(registrationData.name())
            .balance(BigDecimal.valueOf(0.00))
            .isActive(true)
            .build();

        User user = userRepository.save(newUser);

        inventoryRepository.save(
            Inventory.builder()
            .user(user)
            .build()
        );
        
    }

    public void initiatePasswordRecovery(String email) {
        UserDetails user = userRepository.findByEmail(email);
        if (user == null) {
            throw new IllegalArgumentException("User not found");
        }
    }
    
}
