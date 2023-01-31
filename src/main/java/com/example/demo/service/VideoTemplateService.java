package com.example.demo.service;

import com.example.demo.domain.*;
import com.example.demo.repository.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;

@Component
@AllArgsConstructor
public abstract class VideoTemplateService<T extends VideoTemplate> {
    protected UserRepository userRepository;
    protected ImageRepository imageRepository;
    protected VideoTemplateRepository videoTemplateRepository;

    public User getUserByPrincipal(Principal principal) {
        if (principal == null) {
            return new User();
        }
        return userRepository.findByEmail(principal.getName());
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

    private void save(T video) {
        if (video instanceof Film film) {
            ((FilmRepository) videoTemplateRepository).save(film);
        } else {
            ((SerialRepository) videoTemplateRepository).save((Serial) video);
        }
    }

    public void delete(T video) {
        if (video instanceof Film film) {
            ((FilmRepository) videoTemplateRepository).delete(film);
        } else {
            ((SerialRepository) videoTemplateRepository).delete((Serial) video);
        }
    }

    abstract T getById(long id);

    public void create(Principal principal, T video, MultipartFile file) throws IOException {
        if (!file.isEmpty()) {
            Image image = toImageEntity(file);
            video.setImage(image);
        }
        video.setUser(getUserByPrincipal(principal));
        save(video);
    }

    public void update(Principal principal, T video, MultipartFile file, long id) throws IOException {
        if (!file.isEmpty()) {
            Image image = toImageEntity(file);
            imageRepository.delete(getById(id).getImage());
            video.setImage(image);
        } else {
            Image curImage = getById(id).getImage();
            if (curImage != null) {
                video.setImage(curImage);
            }
        }
        video.setUser(getUserByPrincipal(principal));
        save(video);
    }

    abstract Iterable<T> getAll();

    abstract Iterable<T> getTop5();
}
