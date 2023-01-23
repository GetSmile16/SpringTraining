package com.example.demo.controllers;

import com.example.demo.domain.Serial;
import com.example.demo.repositories.SerialRepository;
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
public class SerialController {
    private final SerialRepository repoSerial;

    public SerialController(SerialRepository repoSerial) {
        this.repoSerial = repoSerial;
    }

    private static final Path UPLOAD_DIRECTORY =  Path.of(System.getProperty("user.dir"),"src", "main", "uploads", "images");
    private static final String SERIALS = "serials";
    private static final String EDIT_REDIRECT = "redirect:/api/serials/edit";

    @GetMapping("/serials")
    public String viewSerials(Model model) {
        model.addAttribute(
                SERIALS, repoSerial.findAll());
        return "serial/viewSerials";
    }

    @GetMapping("/serials/edit")
    public String viewEditSerials(Model model) {
        model.addAttribute("films", repoSerial.findAll());
        return "serial/viewEditSerials";
    }

    @GetMapping("/serials/edit/{id}")
    public String editSerial(Model model, @PathVariable long id) {
        model.addAttribute("serial", repoSerial.findById(id));
        return "serial/editSerial";
    }

    @PostMapping("/serials/edit/{id}")
    public String editSerial(@ModelAttribute Serial serial, @RequestParam(name = "data", required = false) MultipartFile multipart) throws IOException {
        Path path = UPLOAD_DIRECTORY.resolve(
                Path.of(
                        SERIALS,
                        multipart.getOriginalFilename()
                )
        );
        if (!multipart.isEmpty()) {
            if (!(new File(path.toUri())).exists()) {
                Files.write(path, multipart.getBytes());
            }
            serial.setImage("uploads/images/serials/" + multipart.getOriginalFilename());
        } else {
            String curSerialImage = repoSerial
                    .findById(serial.getId())
                    .getImage();
            if (curSerialImage != null) {
                serial.setImage(curSerialImage);
            }
        }
        repoSerial.save(serial);
        return EDIT_REDIRECT;
    }

    @GetMapping("/serials/create")
    public String createSerial() {
        return "serial/createSerial";
    }

    @PostMapping("/serials/create")
    public String createSerial(@ModelAttribute Serial serial, @RequestParam(name = "data", required = false) MultipartFile multipart) throws IOException {
        Path path = UPLOAD_DIRECTORY.resolve(
                Path.of(
                        SERIALS,
                        multipart.getOriginalFilename()
                )
        );
        if (!multipart.isEmpty())  {
            if (!(new File(path.toUri())).exists()) {
                Files.write(path, multipart.getBytes());
            }
            serial.setImage("uploads/images/serials/" + multipart.getOriginalFilename());
        }
        repoSerial.save(serial);
        return EDIT_REDIRECT;
    }

    @GetMapping("/serials/delete/{id}")
    public String deleteSerials(Model model, @PathVariable long id) {
        model.addAttribute("serial", repoSerial.findById(id));
        return "serial/deleteSerial";
    }

    @DeleteMapping("/serials/delete/{id}")
    public String deleteSerial(@ModelAttribute Serial serial) {
        repoSerial.delete(serial);
        return EDIT_REDIRECT;
    }
}
