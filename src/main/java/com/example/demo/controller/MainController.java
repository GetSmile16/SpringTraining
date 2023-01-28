package com.example.demo.controller;

import com.example.demo.service.FilmServiceImpl;
import com.example.demo.service.SerialServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class MainController {
    private final SerialServiceImpl serialService;
    private final FilmServiceImpl filmService;

    @GetMapping("/home")
    public String home(Model model, Principal principal) {
        model.addAttribute(
                "films",
                filmService.getTop5()
        );
        model.addAttribute(
                "serials",
                serialService.getTop5()
        );
        model.addAttribute("user", filmService.getUserByPrincipal(principal));
        return "views/home";
    }
}
