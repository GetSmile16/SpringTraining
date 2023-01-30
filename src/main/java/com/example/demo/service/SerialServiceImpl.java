package com.example.demo.service;

import com.example.demo.domain.Serial;
import com.example.demo.repository.ImageRepository;
import com.example.demo.repository.SerialRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

@Service
public class SerialServiceImpl extends VideoTemplateService<Serial> {
    @Autowired
    public SerialServiceImpl(UserRepository userRepository, ImageRepository imageRepository, SerialRepository serialRepository) {
        super(userRepository, imageRepository, serialRepository);
    }
    @Override
    public Serial getById(long id) {
        return ((SerialRepository) videoTemplateRepository)
                .findById(id)
                .orElseThrow(() -> new HttpClientErrorException(HttpStatus.BAD_REQUEST));
    }

    @Override
    public Iterable<Serial> getAll() {
        return ((SerialRepository) videoTemplateRepository).findAll();
    }

    @Override
    public Iterable<Serial> getTop5() {
        return ((SerialRepository) videoTemplateRepository).findFirst5ByOrderByRatingDesc();
    }
}
