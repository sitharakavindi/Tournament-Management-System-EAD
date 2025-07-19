package com.example.TournamentRegistration.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {

    @GetMapping("/login")
    public String login() {

        return "login";
    }

    @GetMapping("/")
    public String home(Authentication authentication) {

        if (authentication != null && authentication.isAuthenticated()) {
            for (GrantedAuthority auth : authentication.getAuthorities()) {
                if ("ROLE_ORGANIZER".equals(auth.getAuthority())) {
                    return "redirect:/organizer/dashboard";
                }
                if ("ROLE_PLAYER".equals(auth.getAuthority())) {
                    return "redirect:/player/dashboard";
                }
            }
        }

        return "index";
    }


    @GetMapping("/index")
    public String showIndexPageExplicit() {
        return "index";
    }
}
