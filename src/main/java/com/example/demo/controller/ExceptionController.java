package com.example.demo.controller;

import com.example.demo.domain.User;
import com.example.demo.domain.enums.Role;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;

@ControllerAdvice
public class ExceptionController {
    public static void handleForbiddenIfNotAdmin (User user) {
        if (!user.getRoles().contains(Role.ROLE_ADMIN)) {
            throw new HttpClientErrorException(HttpStatus.FORBIDDEN);
        }
    }

    @ExceptionHandler(HttpClientErrorException.class)
    public ResponseEntity<?> httpClientErrorException(HttpClientErrorException ex) {
        return new ResponseEntity<>(ex.getStatusCode());
    }

}
