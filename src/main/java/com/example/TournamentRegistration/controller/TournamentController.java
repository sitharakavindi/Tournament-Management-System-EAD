package com.example.TournamentRegistration.controller;

import com.example.TournamentRegistration.model.Tournament;
import com.example.TournamentRegistration.model.User;
import com.example.TournamentRegistration.repository.UserRepository;
import com.example.TournamentRegistration.service.RegistrationService;
import com.example.TournamentRegistration.service.TournamentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@RestController
@RequestMapping("/tournaments")
public class TournamentController {

    private final TournamentService tournamentService;
    private final RegistrationService registrationService;
    private final UserRepository userRepository;

    @Autowired
    public TournamentController(TournamentService tournamentService, RegistrationService registrationService, UserRepository userRepository) {
        this.tournamentService = tournamentService;
        this.registrationService = registrationService;
        this.userRepository = userRepository;
    }

    @GetMapping("/browse")
    public String browseTournaments(Model model) {
        List<Tournament> tournaments = tournamentService.findAllTournaments();
        model.addAttribute("tournaments", tournaments);
        return "browse-tournaments";
    }

    @GetMapping("/{id}/register")
    public String registerForTournament(@PathVariable Long id, Authentication authentication, RedirectAttributes redirectAttributes) {
        if (authentication == null) {
            return "redirect:/login";
        }

        String email = authentication.getName();
        User player = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Player not found with email: " + email));

        Tournament tournament = tournamentService.findTournamentById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid tournament ID:" + id));

        try {
            registrationService.registerPlayerForTournament(player, tournament);
            redirectAttributes.addFlashAttribute("successMessage", "Successfully registered for '" + tournament.getName() + "'!");
        } catch (IllegalStateException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }

        return "redirect:/tournaments/browse";
    }
}
