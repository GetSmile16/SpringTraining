package com.example.demo.service;

import com.example.demo.domain.Film;
import com.example.demo.domain.Image;
import com.example.demo.repository.FilmRepository;
import com.example.demo.repository.ImageRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
@Transactional
public class FilmServiceImpl implements FilmService {
    private final FilmRepository filmRepository;
    private final ImageRepository imageRepository;

    public void createFilm(Film film, MultipartFile file) throws IOException {
        if (!file.isEmpty()) {
            Image image = toImageEntity(file);
            film.setImage(image);
        }
        filmRepository.save(film);
    }

    public void updateFilm(Film film, MultipartFile file, long id) throws IOException {
        if (!file.isEmpty()) {
            Image image = toImageEntity(file);
            imageRepository.delete(filmRepository.findById(id).get().getImage());
            film.setImage(image);
        } else {
            Image curImage = filmRepository.findById(film.getId()).get().getImage();
            if (curImage != null) {
                film.setImage(curImage);
            }
        }
        filmRepository.save(film);
    }

    private Image toImageEntity(MultipartFile file) throws IOException {
        return Image.builder()
                .name(file.getName())
                .originalFileName(file.getOriginalFilename())
                .size(file.getSize())
                .contentType(file.getContentType())
                .bytes(file.getBytes())
                .build();
    }

    @Override
    public Iterable<Film> getAllFilms() {
        return filmRepository.findAll();
    }

    @Override
    public void deleteFilm(Film film) {
        filmRepository.delete(film);
    }

    @Override
    public Iterable<Film> getTop5() {
        return filmRepository.findFirst5ByOrderByRatingDesc();
    }

    @Override
    public Film getFilmById(long id) {
        return filmRepository.findById(id).orElse(null);
    }
}
