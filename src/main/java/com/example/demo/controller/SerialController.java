package com.example.demo.controller;

import com.example.demo.domain.Serial;
import com.example.demo.service.SerialServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequiredArgsConstructor
public class SerialController {
    private final SerialServiceImpl serialService;
    private static final String SERIALS = "serials";
    private static final String EDIT_REDIRECT = "redirect:/serials/edit";

    @GetMapping("/serials")
    public String viewSerials(Model model) {
        model.addAttribute(SERIALS, serialService.getAllSerials());
        return "serial/viewSerials";
    }


    @GetMapping("/serials/edit")
    public String viewEditSerials(Model model) {
        model.addAttribute(SERIALS, serialService.getAllSerials());
        return "serial/viewEditSerials";
    }

    @GetMapping("/serials/edit/{id}")
    public String editSerial(Model model, @PathVariable long id) {
        model.addAttribute("serial", serialService.getSerialById(id));
        return "serial/editSerial";
    }

    @PostMapping("/serials/edit/{id}")
    public String editSerial(@ModelAttribute Serial serial, @RequestParam(name = "data", required = false) MultipartFile multipart, @PathVariable long id) throws IOException {
        serialService.updateSerial(serial, multipart, id);
        return EDIT_REDIRECT;
    }

    @GetMapping("/serials/create")
    public String createSerial() {
        return "serial/createSerial";
    }

    @PostMapping("/serials/create")
    public String createSerial(@ModelAttribute Serial serial, @RequestParam(name = "data", required = false) MultipartFile multipart) throws IOException {
        serialService.createSerial(serial, multipart);
        return EDIT_REDIRECT;
    }

    @GetMapping("/serials/delete/{id}")
    public String deleteSerial(Model model, @PathVariable long id) {
        model.addAttribute("serial", serialService.getSerialById(id));
        return "serial/deleteSerial";
    }

    @DeleteMapping("/serials/delete/{id}")
    public String deleteSerial(@ModelAttribute Serial serial) {
        serialService.deleteSerial(serial);
        return EDIT_REDIRECT;
    }
}
