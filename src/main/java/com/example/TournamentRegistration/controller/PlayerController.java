package com.example.TournamentRegistration.controller;

import com.example.TournamentRegistration.model.Registration;
import com.example.TournamentRegistration.model.User;
import com.example.TournamentRegistration.repository.UserRepository;
import com.example.TournamentRegistration.service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/player")
public class PlayerController {

    private final RegistrationService registrationService;
    private final UserRepository userRepository;

    @Autowired
    public PlayerController(RegistrationService registrationService, UserRepository userRepository) {
        this.registrationService = registrationService;
        this.userRepository = userRepository;
    }

    @GetMapping("/dashboard")
    public String playerDashboard(Model model, Authentication authentication) {
        String email = authentication.getName();
        User player = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Player not found"));

        List<Registration> registrations = registrationService.findRegistrationsByPlayer(player);
        model.addAttribute("registrations", registrations);

        return "player-dashboard";
    }
}
