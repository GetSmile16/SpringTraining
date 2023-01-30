package com.example.demo.controller;

import com.example.demo.domain.User;
import com.example.demo.service.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
public class UserController {
    private final UserServiceImpl userService;
    @GetMapping("/login")
    public String login() {
        return "views/security/login";
    }

    @GetMapping("/login-error")
    public String loginError(final RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute(
                        "errorMessage",
                        "Введен неправильный логин или пароль"
        );
        return "redirect:/login";
    }

    @GetMapping("/registration")
    public String registration() {
        return "views/security/registration";
    }

    @PostMapping("registration")
    public String createUser(@ModelAttribute User user, Model model) {
        if (!userService.createUser(user)) {
            model.addAttribute(
                    "errorMessage",
                    "Пользователь с email: " + user.getEmail() + " уже существует"
            );
            return "views/security/registration";
        }
        userService.createUser(user);
        return "redirect:/login";
    }
}
