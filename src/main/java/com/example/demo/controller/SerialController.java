package com.example.demo.controller;

import com.example.demo.domain.Serial;
import com.example.demo.domain.User;
import com.example.demo.service.SerialServiceImpl;
import lombok.RequiredArgsConstructor;
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
        User user = serialService.getUserByPrincipal(principal);
        ExceptionController.handleForbiddenIfNotAdmin(user);
        model.addAttribute(SERIALS, serialService.getAll());
        model.addAttribute("user", serialService.getUserByPrincipal(principal));
        return "views/serial/viewSerials";
    }

    @GetMapping("/serials/edit")
    public String viewEditSerials(Model model, Principal principal) {
        User user = serialService.getUserByPrincipal(principal);
        ExceptionController.handleForbiddenIfNotAdmin(user);
        model.addAttribute(SERIALS, serialService.getAll());
        model.addAttribute("user", user);
        return "views/serial/viewEditSerials";
    }

    @GetMapping("/serials/edit/{id}")
    public String editSerial(Model model,
                             @PathVariable long id,
                             Principal principal) {
        User user = serialService.getUserByPrincipal(principal);
        ExceptionController.handleForbiddenIfNotAdmin(user);
        model.addAttribute("serial", serialService.getById(id));
        model.addAttribute("user", serialService.getUserByPrincipal(principal));
        return "views/serial/editSerial";
    }

    @PostMapping("/serials/edit/{id}")
    public String editSerial(@ModelAttribute Serial serial,
                             @RequestParam(name = "data", required = false) MultipartFile multipart,
                             @PathVariable long id,
                             Principal principal) throws IOException {
        User user = serialService.getUserByPrincipal(principal);
        ExceptionController.handleForbiddenIfNotAdmin(user);
        serialService.update(principal, serial, multipart, id);
        return EDIT_REDIRECT;
    }

    @GetMapping("/serials/create")
    public String createSerial(Model model, Principal principal) {
        User user = serialService.getUserByPrincipal(principal);
        ExceptionController.handleForbiddenIfNotAdmin(user);
        model.addAttribute("user", serialService.getUserByPrincipal(principal));
        return "views/serial/createSerial";
    }

    @PostMapping("/serials/create")
    public String createSerial(@ModelAttribute Serial serial,
                               @RequestParam(name = "data", required = false) MultipartFile multipart,
                               Principal principal) throws IOException {
        User user = serialService.getUserByPrincipal(principal);
        ExceptionController.handleForbiddenIfNotAdmin(user);
        serialService.create(principal, serial, multipart);
        return EDIT_REDIRECT;
    }

    @GetMapping("/serials/delete/{id}")
    public String deleteSerial(Model model,
                               @PathVariable long id,
                               Principal principal) {
        User user = serialService.getUserByPrincipal(principal);
        ExceptionController.handleForbiddenIfNotAdmin(user);
        model.addAttribute("serial", serialService.getById(id));
        model.addAttribute("user", serialService.getUserByPrincipal(principal));
        return "views/serial/deleteSerial";
    }

    @DeleteMapping("/serials/delete/{id}")
    public String deleteSerial(@ModelAttribute Serial serial, Principal principal) {
        User user = serialService.getUserByPrincipal(principal);
        ExceptionController.handleForbiddenIfNotAdmin(user);
        serialService.delete(serial);
        return EDIT_REDIRECT;
    }
}
