package com.example.demo.controller;

import com.example.demo.domain.Serial;
import com.example.demo.service.SerialServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class SerialController {
    private final SerialServiceImpl serialService;
    private static final String SERIALS = "serials";
    private static final String EDIT_REDIRECT = "redirect:/serials/edit";

    @GetMapping("/serials")
    public String viewSerials(Model model, Principal principal) {
        model.addAttribute(SERIALS, serialService.getAll());
        model.addAttribute("user", serialService.getUserByPrincipal(principal));
        return "views/serial/viewSerials";
    }

    @GetMapping("/serials/edit")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String viewEditSerials(Model model, Principal principal) {
        model.addAttribute(SERIALS, serialService.getAll());
        model.addAttribute("user", serialService.getUserByPrincipal(principal));
        return "views/serial/viewEditSerials";
    }

    @GetMapping("/serials/edit/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String editSerial(Model model,
                             @PathVariable long id,
                             Principal principal) {
        model.addAttribute("serial", serialService.getById(id));
        model.addAttribute("user", serialService.getUserByPrincipal(principal));
        return "views/serial/editSerial";
    }

    @PostMapping("/serials/edit/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String editSerial(@ModelAttribute Serial serial,
                             @RequestParam(name = "data", required = false) MultipartFile multipart,
                             @PathVariable long id,
                             Principal principal) throws IOException {
        serialService.update(principal, serial, multipart, id);
        return EDIT_REDIRECT;
    }

    @GetMapping("/serials/create")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String createSerial(Model model, Principal principal) {
        model.addAttribute("user", serialService.getUserByPrincipal(principal));
        return "views/serial/createSerial";
    }

    @PostMapping("/serials/create")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String createSerial(@ModelAttribute Serial serial,
                               @RequestParam(name = "data", required = false) MultipartFile multipart,
                               Principal principal) throws IOException {
        serialService.create(principal, serial, multipart);
        return EDIT_REDIRECT;
    }

    @GetMapping("/serials/delete/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String deleteSerial(Model model,
                               @PathVariable long id,
                               Principal principal) {
        model.addAttribute("serial", serialService.getById(id));
        model.addAttribute("user", serialService.getUserByPrincipal(principal));
        return "views/serial/deleteSerial";
    }

    @DeleteMapping("/serials/delete/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String deleteSerial(@ModelAttribute Serial serial) {
        serialService.delete(serial);
        return EDIT_REDIRECT;
    }
}
