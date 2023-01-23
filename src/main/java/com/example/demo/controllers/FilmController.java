package com.example.demo.controllers;

import com.example.demo.domain.Film;
import com.example.demo.repositories.FilmRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Controller
@RequestMapping("/api")
public class FilmController {

    private final FilmRepository repoFilm;

    public FilmController(FilmRepository repoFilm) {
        this.repoFilm = repoFilm;
    }

    private static final Path UPLOAD_DIRECTORY =  Path.of(System.getProperty("user.dir"),"src", "main", "uploads", "images");
    private static final String FILMS = "films";
    private static final String EDIT_REDIRECT = "redirect:/api/films/edit";

    @GetMapping("/films")
    public String viewFilms(Model model) {
        model.addAttribute(FILMS, repoFilm.findAll());
        return "film/viewFilms";
    }


    @GetMapping("/films/edit")
    public String viewEditFilms(Model model) {
        model.addAttribute(FILMS, repoFilm.findAll());
        return "film/viewEditFilms";
    }

    @GetMapping("/films/edit/{id}")
    public String editFilm(Model model, @PathVariable long id) {
        model.addAttribute(FILMS, repoFilm.findById(id));
        return "film/editFilm";
    }

    @PostMapping("/films/edit/{id}")
    public String editFilm(@ModelAttribute Film film, @RequestParam(name = "data", required = false) MultipartFile multipart) throws IOException {
        Path path = UPLOAD_DIRECTORY.resolve(
                Path.of(
                        FILMS,
                        multipart.getOriginalFilename()
                )
        );
        if (!multipart.isEmpty())  {
            if (!(new File(path.toUri())).exists()) {
                Files.write(path, multipart.getBytes());
            }
            film.setImage("uploads/images/films/" + multipart.getOriginalFilename());
        } else {
            String curFilmImage = repoFilm
                    .findById(film.getId())
                    .getImage();
            if (curFilmImage != null) {
                film.setImage(curFilmImage);
            }
        }
        repoFilm.save(film);
        return EDIT_REDIRECT;
    }

    @GetMapping("/films/create")
    public String createFilm() {
        return "film/createFilm";
    }

    @PostMapping("/films/create")
    public String createFilm(@ModelAttribute Film film, @RequestParam(name = "data", required = false) MultipartFile multipart) throws IOException {
        Path path = UPLOAD_DIRECTORY.resolve(
                Path.of(
                        FILMS,
                        multipart.getOriginalFilename()
                )
        );
        if (!multipart.isEmpty())  {
            if (!(new File(path.toUri())).exists()) {
                Files.write(path, multipart.getBytes());
            }
            film.setImage("uploads/images/films/" + multipart.getOriginalFilename());
        }
        repoFilm.save(film);
        return EDIT_REDIRECT;
    }

    @GetMapping("/films/delete/{id}")
    public String deleteFilm(Model model, @PathVariable long id) {
        model.addAttribute("film", repoFilm.findById(id));
        return "film/deleteFilm";
    }

    @DeleteMapping("/films/delete/{id}")
    public String deleteFilm(@ModelAttribute Film film) {
        repoFilm.delete(film);
        return EDIT_REDIRECT;
    }
}
