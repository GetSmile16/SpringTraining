package com.example.demo.controllers;

import com.example.demo.domain.Film;
import com.example.demo.repositories.FilmRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
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
@EnableJpaRepositories
public class MainController {
    @Autowired
    FilmRepository repoFilm;

    @Autowired
    @Qualifier("imagePath")
    String imagePath;
    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public String home(Model model) {
        model.addAttribute("main", "You're welcome!");
        return "home";
    }
    @RequestMapping(value = "/films", method = RequestMethod.GET)
    public String viewFilms(Model model) {
        model.addAttribute("films", repoFilm.findAll());
        return "viewFilms";
    }

    @RequestMapping(value = "/films/edit", method = RequestMethod.GET)
    public String viewEditFilms(Model model) {
        model.addAttribute("films", repoFilm.findAll());
        return "viewEditFilms";
    }

    @RequestMapping(value = "films/edit/{id}", method = RequestMethod.GET)
    public String editFilm(Model model, @PathVariable long id) {
        model.addAttribute("film", repoFilm.findById(id));
        return "editFilm";
    }

    @RequestMapping(value = "films/edit/{id}", method = RequestMethod.POST)
    public String editFilm(@ModelAttribute Film film, @RequestParam(name = "data", required = false) MultipartFile multipart) throws IOException {
        String path = imagePath + "films\\"+ multipart.getOriginalFilename();
        if (!(new File(path)).exists()) {
            Files.write(Path.of(path), multipart.getBytes());
        }
        film.setImage("/imgs/films/" + multipart.getOriginalFilename());
        repoFilm.save(film);
        return "redirect:/api/films/edit";
    }

    @RequestMapping(value = "films/create", method = RequestMethod.GET)
    public String createFilm() {
        return "createFilm";
    }

    @RequestMapping(value = "films/create", method = RequestMethod.POST)
    public String createFilm(@ModelAttribute Film film, @RequestParam(name = "data", required = false) MultipartFile multipart) throws IOException {
        String path = imagePath + "films\\"+ multipart.getOriginalFilename();
        if (!multipart.isEmpty() && !(new File(path)).exists())  {
            Files.write(Path.of(path), multipart.getBytes());
        }
        film.setImage("/imgs/films/" + multipart.getOriginalFilename());
        repoFilm.save(film);
        return "redirect:/api/films/edit";
    }

    @RequestMapping(value = "films/delete/{id}", method = RequestMethod.GET)
    public String deleteFilm(Model model, @PathVariable long id) {
        model.addAttribute("film", repoFilm.findById(id));
        return "deleteFilm";
    }

    @RequestMapping(value = "films/delete/{id}", method = RequestMethod.DELETE)
    public String deleteFilm(@ModelAttribute Film film) {
        repoFilm.delete(film);
        return "redirect:/api/films/edit";
    }

}
