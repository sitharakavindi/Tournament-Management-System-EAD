package com.example.TournamentRegistration.controller;

import com.example.TournamentRegistration.dto.UserRegistrationDto;
import com.example.TournamentRegistration.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RestController
@RequestMapping("/registration")
public class UserRegistrationController {

    private final UserService userService;

    @Autowired
    public UserRegistrationController(UserService userService) {
        this.userService = userService;
    }

    @ModelAttribute("user")
    public UserRegistrationDto userRegistrationDto() {
        return new UserRegistrationDto();
    }

    @GetMapping("/organizer")
    public String showOrganizerRegistrationForm() {
        return "organizer-registration";
    }

    @PostMapping("/organizer")
    public String registerOrganizerAccount(@ModelAttribute("user") UserRegistrationDto registrationDto, RedirectAttributes redirectAttributes) {
        try {
            userService.saveOrganizer(registrationDto);
            return "redirect:/registration/organizer?success";
        } catch (IllegalStateException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/registration/organizer?error";
        }
    }

    @GetMapping("/player")
    public String showPlayerRegistrationForm() {
        return "player-registration";
    }

    @PostMapping("/player")
    public String registerPlayerAccount(@ModelAttribute("user") UserRegistrationDto registrationDto, RedirectAttributes redirectAttributes) {
        try {
            userService.savePlayer(registrationDto);
            return "redirect:/registration/player?success";
        } catch (IllegalStateException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/registration/player?error";
        }
    }
}
