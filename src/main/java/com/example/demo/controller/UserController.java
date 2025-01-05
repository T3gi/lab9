package com.example.demo.controller;

import com.example.demo.model.Developer;
import com.example.demo.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    public String addRegisterForm(Model model) {
        model.addAttribute("user", new Developer());
        return "register";
    }

    @GetMapping("/login")
    public String addLoginForm(Model model) {
        model.addAttribute("user", new Developer());
        return "login";
    }

    @PostMapping("/add_user")
    public String saveUser(@ModelAttribute Developer developer) {
        userService.save(developer);
        return "redirect:/login";
    }

    @PostMapping("/login")
    public String changeUser(@ModelAttribute Developer developer) {
        String name = developer.getName();
        String pass = developer.getPassword();
        developer = userService.findByName(name, pass);
        if (developer != null) {
            developer.setCurrent(true);
            userService.save(developer);
            return "redirect:/";
        } else {
            return "redirect:/login";
        }
    }

    @PostMapping("/unlogin")
    public String resetUser() {
        Developer developer = userService.findCurrent();
        if (developer != null) {
            developer.setCurrent(false);
            userService.save(developer);
        }
        return "redirect:/";
    }
}
