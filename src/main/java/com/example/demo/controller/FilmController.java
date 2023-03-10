package com.example.demo.controller;

import com.example.demo.domain.Film;
import com.example.demo.service.FilmServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String viewEditFilms(Model model, Principal principal) {
        model.addAttribute(FILMS, filmService.getAll());
        model.addAttribute("user", filmService.getUserByPrincipal(principal));
        return "views/film/viewEditFilms";
    }

    @GetMapping("/films/edit/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String editFilm(Model model,
                           @PathVariable long id,
                           Principal principal) {
        if (!model.containsAttribute("film")) {
            model.addAttribute("film", filmService.getById(id));
        }
        model.addAttribute("user", filmService.getUserByPrincipal(principal));
        return "views/film/editFilm";
    }

    @PostMapping("/films/edit/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String editFilm(@ModelAttribute @Valid Film film,
                           BindingResult bindingResult,
                           @RequestParam(name = "data", required = false) MultipartFile multipart,
                           @PathVariable long id,
                           Principal principal,
                           RedirectAttributes rd) throws IOException {
        if (bindingResult.hasErrors()) {
            film.setImage(filmService.getById(id).getImage());
            rd.addFlashAttribute("org.springframework.validation.BindingResult.film", bindingResult);
            rd.addFlashAttribute("film", film);
            return "redirect:/films/edit/" + id;
        }
        filmService.update(principal, film, multipart, id);
        return EDIT_REDIRECT;
    }

    @GetMapping("/films/create")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String createFilm(Model model, Principal principal) {
        if (!model.containsAttribute("film")) {
            model.addAttribute("film", new Film());
        }
        model.addAttribute("user", filmService.getUserByPrincipal(principal));
        return "views/film/createFilm";
    }

    @PostMapping("/films/create")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String createFilm(@ModelAttribute @Valid Film film,
                             BindingResult bindingResult,
                             @RequestParam(name = "data", required = false) MultipartFile multipart,
                             Principal principal,
                             RedirectAttributes rd) throws IOException {
        if (bindingResult.hasErrors()) {
            rd.addFlashAttribute("org.springframework.validation.BindingResult.film", bindingResult);
            rd.addFlashAttribute("film", film);
            return "redirect:/films/create";
        }
        filmService.create(principal, film, multipart);
        return EDIT_REDIRECT;
    }

    @GetMapping("/films/delete/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String deleteFilm(Model model,
                             @PathVariable long id,
                             Principal principal) {
        model.addAttribute("film", filmService.getById(id));
        model.addAttribute("user", filmService.getUserByPrincipal(principal));
        return "views/film/deleteFilm";
    }

    @DeleteMapping("/films/delete/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String deleteFilm(@ModelAttribute @Valid Film film) {
        filmService.delete(film);
        return EDIT_REDIRECT;
    }
}
