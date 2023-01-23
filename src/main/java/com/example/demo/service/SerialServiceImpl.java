package com.example.demo.service;

import com.example.demo.domain.Serial;
import com.example.demo.domain.Image;
import com.example.demo.repository.ImageRepository;
import com.example.demo.repository.SerialRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
@Transactional
public class SerialServiceImpl implements SerialService {
    private final SerialRepository serialRepository;
    private final ImageRepository imageRepository;

    public void createSerial(Serial serial, MultipartFile file) throws IOException {
        if (!file.isEmpty()) {
            Image image = toImageEntity(file);
            serial.setImage(image);
        }
        serialRepository.save(serial);
    }

    public void updateSerial(Serial serial, MultipartFile file, long id) throws IOException {
        if (!file.isEmpty()) {
            Image image = toImageEntity(file);
            imageRepository.delete(serialRepository.findById(id).get().getImage());
            serial.setImage(image);
        } else {
            Image curImage = serialRepository.findById(serial.getId()).get().getImage();
            if (curImage != null) {
                serial.setImage(curImage);
            }
        }
        serialRepository.save(serial);
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
    public Iterable<Serial> getAllSerials() {
        return serialRepository.findAll();
    }

    @Override
    public void deleteSerial(Serial serial) {
        serialRepository.delete(serial);
    }

    @Override
    public Iterable<Serial> getTop5() {
        return serialRepository.findFirst5ByOrderByRatingDesc();
    }

    @Override
    public Serial getSerialById(long id) {
        return serialRepository.findById(id).orElse(null);
    }
}
