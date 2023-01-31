package com.example.demo.service;

import com.example.demo.domain.Film;
import com.example.demo.repository.FilmRepository;
import com.example.demo.repository.ImageRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

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
                .orElseThrow(() -> new HttpClientErrorException(HttpStatus.BAD_REQUEST));
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
