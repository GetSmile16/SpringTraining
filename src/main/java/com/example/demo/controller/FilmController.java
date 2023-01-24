package com.example.demo.controller;

import com.example.demo.domain.Film;
import com.example.demo.service.FilmServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequiredArgsConstructor
public class FilmController {
    private final FilmServiceImpl filmService;
    private static final String FILMS = "films";
    private static final String EDIT_REDIRECT = "redirect:/films/edit";

    @GetMapping("/films")
    public String viewFilms(Model model) {
        model.addAttribute(FILMS, filmService.getAllFilms());
        return "views/film/viewFilms";
    }


    @GetMapping("/films/edit")
    public String viewEditFilms(Model model) {
        model.addAttribute(FILMS, filmService.getAllFilms());
        return "views/film/viewEditFilms";
    }

    @GetMapping("/films/edit/{id}")
    public String editFilm(Model model, @PathVariable long id) {
        model.addAttribute("film", filmService.getFilmById(id));
        return "views/film/editFilm";
    }

    @PostMapping("/films/edit/{id}")
    public String editFilm(@ModelAttribute Film film, @RequestParam(name = "data", required = false) MultipartFile multipart, @PathVariable long id) throws IOException {
        filmService.updateFilm(film, multipart, id);
        return EDIT_REDIRECT;
    }

    @GetMapping("/films/create")
    public String createFilm() {
        return "views/film/createFilm";
    }

    @PostMapping("/films/create")
    public String createFilm(@ModelAttribute Film film, @RequestParam(name = "data", required = false) MultipartFile multipart) throws IOException {
        filmService.createFilm(film, multipart);
        return EDIT_REDIRECT;
    }

    @GetMapping("/films/delete/{id}")
    public String deleteFilm(Model model, @PathVariable long id) {
        model.addAttribute("film", filmService.getFilmById(id));
        return "views/film/deleteFilm";
    }

    @DeleteMapping("/films/delete/{id}")
    public String deleteFilm(@ModelAttribute Film film) {
        filmService.deleteFilm(film);
        return EDIT_REDIRECT;
    }
}
