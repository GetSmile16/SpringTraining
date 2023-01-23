package com.example.demo.service;

import com.example.demo.domain.Serial;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface SerialService {
    void createSerial(Serial serial, MultipartFile file) throws IOException;
    void updateSerial(Serial serial, MultipartFile file, long id) throws IOException;
    Iterable<Serial> getAllSerials();
    void deleteSerial(Serial serial);
    Iterable<Serial> getTop5();
    Serial getSerialById(long id);
}
