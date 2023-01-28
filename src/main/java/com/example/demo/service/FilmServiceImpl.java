package com.example.demo.service;

import com.example.demo.domain.Film;
import com.example.demo.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FilmServiceImpl extends VideoTemplateService<Film> {

    @Autowired
    public FilmServiceImpl(UserRepository userRepository, ImageRepository imageRepository, FilmRepository filmRepository) {
        super(userRepository, imageRepository, filmRepository);
    }

    @Override
    public Film getById(long id) {
        return ((FilmRepository) videoTemplateRepository)
                    .findById(id)
                    .orElseThrow();
    }

    @Override
    public Iterable<Film> getAll() {
        return ((FilmRepository) videoTemplateRepository).findAll();
    }

    @Override
    public Iterable<Film> getTop5() {
        return ((FilmRepository) videoTemplateRepository).findFirst5ByOrderByRatingDesc();
    }
}
