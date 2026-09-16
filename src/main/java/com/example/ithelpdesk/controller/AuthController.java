package com.example.ithelpdesk.controller;

import com.example.ithelpdesk.model.AppUser;
import com.example.ithelpdesk.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthController(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder) {

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String registerPage() {
        return "register";
    }

    @PostMapping("/register")
    public String register(
            @RequestParam String username,
            @RequestParam String password,
            Model model) {

        if (userRepository.findByUsername(username).isPresent()) {

            model.addAttribute(
                    "error",
                    "Username already exists."
            );

            return "register";
        }

        AppUser user = new AppUser();

        user.setUsername(username);

        user.setPassword(
                passwordEncoder.encode(password)
        );

        // Everyone registering normally is a USER.
        user.setRole("USER");

        userRepository.save(user);

        return "redirect:/login?registered";
    }
}