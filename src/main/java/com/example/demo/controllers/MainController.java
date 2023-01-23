package com.example.demo.controllers;

import com.example.demo.repositories.FilmRepository;
import com.example.demo.repositories.SerialRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api")
public class MainController{
    protected final SerialRepository repoSerial;
    protected final FilmRepository repoFilm;

    @GetMapping("/home")
    public String home(Model model) {
        model.addAttribute(
                "films",
                repoFilm
                        .findAllByOrderByRatingDesc()
                        .stream()
                        .limit(5)
                        .toList()
        );
        model.addAttribute(
                "serials",
                repoSerial
                        .findAllByOrderByRatingDesc()
                        .stream()
                        .limit(5)
                        .toList()
        );
        return "home";
    }
}
