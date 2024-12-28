package com.example.mercatusAPI.exception.auth;

public class ValidationException extends RuntimeException {
    public ValidationException(String message) {
        super(message);
    }
}
