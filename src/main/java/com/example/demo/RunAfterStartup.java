package com.example.demo;

import com.example.demo.domain.User;
import com.example.demo.service.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.HashSet;

@Component
@RequiredArgsConstructor
public class RunAfterStartup {
    private static final String USER = "admin";
    private static final String PASSWORD = "admin";
    private static final String EMAIL = "admin@gmail.com";
    private static final String PHONE = "+78005553535";
    public final UserServiceImpl userService;
    @EventListener(ApplicationReadyEvent.class)
    public void runAfterStartup() {
        User user = User.builder()
                .name(USER)
                .password(PASSWORD)
                .email(EMAIL)
                .phoneNumber(PHONE)
                .roles(new HashSet<>())
                .build();
        userService.createUser(user);
    }
}
