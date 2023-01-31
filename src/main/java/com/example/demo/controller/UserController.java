package com.example.demo.controller;

import com.example.demo.domain.User;
import com.example.demo.service.UserServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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
    public String registration(Model model) {
        if (!model.containsAttribute("user")) {
            model.addAttribute("user", new User());
        }
        return "views/security/registration";
    }

    @PostMapping("/registration")
    public String createUser(@ModelAttribute @Valid User user,
                             BindingResult bindingResult,
                             RedirectAttributes rd) {
        if (bindingResult.hasErrors()) {
            rd.addFlashAttribute("org.springframework.validation.BindingResult.user", bindingResult);
            rd.addFlashAttribute("user", user);
            return "redirect:/registration";
        }
        if (!userService.createUser(user)) {
            rd.addFlashAttribute(
                    "errorMessage",
                    "Пользователь с email: " + user.getEmail() + " уже существует"
            );
            rd.addFlashAttribute("user", user);
            return "redirect:/registration";
        }
        userService.createUser(user);
        return "redirect:/login";
    }
}
