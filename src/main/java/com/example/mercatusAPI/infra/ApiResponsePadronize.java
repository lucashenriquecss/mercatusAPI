package com.example.mercatusAPI.infra;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiResponsePadronize<T> {

    private HttpStatus status;
    private String message;
    private T data;

    public ApiResponsePadronize(HttpStatus status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public ApiResponsePadronize(HttpStatus status, String message) {
        this(status, message, null);
    }
}