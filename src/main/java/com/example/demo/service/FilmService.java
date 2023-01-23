package com.example.demo.service;

import com.example.demo.domain.Film;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FilmService {
    void createFilm(Film film, MultipartFile file) throws IOException;
    void updateFilm(Film film, MultipartFile file, long id) throws IOException;
    Iterable<Film> getAllFilms();
    void deleteFilm(Film film);
    Iterable<Film> getTop5();
    Film getFilmById(long id);
}
