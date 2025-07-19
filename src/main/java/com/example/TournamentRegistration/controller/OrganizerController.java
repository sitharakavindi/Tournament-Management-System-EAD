package com.example.TournamentRegistration.controller;

import com.example.TournamentRegistration.model.Registration;
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
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/organizer")
public class OrganizerController {

    private final TournamentService tournamentService;
    private final UserRepository userRepository;
    private final RegistrationService registrationService;

    @Autowired
    public OrganizerController(TournamentService tournamentService, UserRepository userRepository, RegistrationService registrationService) {
        this.tournamentService = tournamentService;
        this.userRepository = userRepository;
        this.registrationService = registrationService;
    }

    @GetMapping("/dashboard")
    public String organizerDashboard(Model model, Authentication authentication) {

        String email = authentication.getName();
        User organizer = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Organizer not found"));


        Map<String, Long> stats = tournamentService.getOrganizerDashboardStats(organizer);


        model.addAttribute("stats", stats);

        return "organizer-dashboard";
    }

    @GetMapping("/tournaments")
    public String showMyTournaments(Model model, Authentication authentication) {
        String email = authentication.getName();
        User organizer = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Organizer not found"));
        List<Tournament> tournaments = tournamentService.findTournamentsByOrganizer(organizer);
        model.addAttribute("tournaments", tournaments);
        return "my-tournaments";
    }

    @GetMapping("/tournaments/new")
    public String showNewTournamentForm(Model model) {
        Tournament tournament = new Tournament();
        model.addAttribute("tournament", tournament);
        return "create-tournament";
    }

    @PostMapping("/tournaments/save")
    public String saveTournament(@ModelAttribute("tournament") Tournament tournament, Authentication authentication) {
        String email = authentication.getName();
        User organizer = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Organizer not found"));
        tournament.setOrganizer(organizer);
        tournamentService.saveTournament(tournament);
        return "redirect:/organizer/tournaments";
    }

    @GetMapping("/tournaments/edit/{id}")
    public String showEditTournamentForm(@PathVariable Long id, Model model) {
        Optional<Tournament> tournamentOpt = tournamentService.findTournamentById(id);
        if (tournamentOpt.isPresent()) {
            model.addAttribute("tournament", tournamentOpt.get());
            return "create-tournament";
        } else {
            return "redirect:/organizer/tournaments";
        }
    }

    @DeleteMapping("/tournaments/delete/{id}")
    public String deleteTournament(@PathVariable Long id) {
        tournamentService.deleteTournamentById(id);
        return "redirect:/organizer/tournaments";
    }

    @GetMapping("/tournaments/{id}/registrations")
    public String viewRegistrations(@PathVariable Long id, Model model) {
        Tournament tournament = tournamentService.findTournamentById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid tournament ID:" + id));
        List<Registration> registrations = registrationService.findRegistrationsByTournament(tournament);

        model.addAttribute("tournament", tournament);
        model.addAttribute("registrations", registrations);
        return "view-registrations";
    }
}
