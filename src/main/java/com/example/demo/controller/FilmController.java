package com.example.demo.controller;

import com.example.demo.domain.Film;
import com.example.demo.domain.User;
import com.example.demo.service.FilmServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class FilmController {
    private final FilmServiceImpl filmService;
    private static final String FILMS = "films";
    private static final String EDIT_REDIRECT = "redirect:/films/edit";

    @GetMapping("/films")
    public String viewFilms(Model model, Principal principal) {
        model.addAttribute(FILMS, filmService.getAll());
        model.addAttribute("user", filmService.getUserByPrincipal(principal));
        return "views/film/viewFilms";
    }

    @GetMapping("/films/edit")
    public String viewEditFilms(Model model, Principal principal) {
        User user = filmService.getUserByPrincipal(principal);
        ExceptionController.handleForbiddenIfNotAdmin(user);
        model.addAttribute(FILMS, filmService.getAll());
        model.addAttribute("user", user);
        return "views/film/viewEditFilms";
    }

    @GetMapping("/films/edit/{id}")
    public String editFilm(Model model,
                           @PathVariable long id,
                           Principal principal) {
        User user = filmService.getUserByPrincipal(principal);
        ExceptionController.handleForbiddenIfNotAdmin(user);
        model.addAttribute("film", filmService.getById(id));
        model.addAttribute("user", user);
        return "views/film/editFilm";
    }

    @PostMapping("/films/edit/{id}")
    public String editFilm(@ModelAttribute Film film,
                           @RequestParam(name = "data", required = false) MultipartFile multipart,
                           @PathVariable long id,
                           Principal principal) throws IOException {
        ExceptionController.handleForbiddenIfNotAdmin(
                filmService.getUserByPrincipal(principal)
        );
        filmService.update(principal, film, multipart, id);
        return EDIT_REDIRECT;
    }

    @GetMapping("/films/create")
    public String createFilm(Model model, Principal principal) {
        User user = filmService.getUserByPrincipal(principal);
        ExceptionController.handleForbiddenIfNotAdmin(user);
        model.addAttribute("user", user);
        return "views/film/createFilm";
    }

    @PostMapping("/films/create")
    public String createFilm(@ModelAttribute Film film,
                             @RequestParam(name = "data", required = false) MultipartFile multipart,
                             Principal principal) throws IOException {
        ExceptionController.handleForbiddenIfNotAdmin(filmService.getUserByPrincipal(principal));
        filmService.create(principal, film, multipart);
        return EDIT_REDIRECT;
    }

    @GetMapping("/films/delete/{id}")
    public String deleteFilm(Model model,
                             @PathVariable long id,
                             Principal principal) {
        User user = filmService.getUserByPrincipal(principal);
        ExceptionController.handleForbiddenIfNotAdmin(user);
        model.addAttribute("film", filmService.getById(id));
        model.addAttribute("user", user);
        return "views/film/deleteFilm";
    }

    @DeleteMapping("/films/delete/{id}")
    public String deleteFilm(@ModelAttribute Film film, Principal principal) {
        ExceptionController.handleForbiddenIfNotAdmin(
                filmService.getUserByPrincipal(principal)
        );
        filmService.delete(film);
        return EDIT_REDIRECT;
    }
}
