package com.example.demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;

@ControllerAdvice
public class ExceptionController {
    @ExceptionHandler(HttpClientErrorException.class)
    public ResponseEntity<?> httpClientErrorException(HttpClientErrorException ex) {
        return new ResponseEntity<>(ex.getStatusCode());
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<?> accessDeniedException() {
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

}
