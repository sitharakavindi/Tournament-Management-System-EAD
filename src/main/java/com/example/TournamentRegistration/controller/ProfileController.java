package com.example.TournamentRegistration.controller;

import com.example.TournamentRegistration.model.User;
import com.example.TournamentRegistration.repository.UserRepository;
import com.example.TournamentRegistration.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RestController
@RequestMapping("/profile")
public class ProfileController {

    private final UserService userService;
    private final UserRepository userRepository;

    @Autowired
    public ProfileController(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @GetMapping
    public String showProfilePage(Model model, Authentication authentication) {
        String email = authentication.getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        model.addAttribute("user", user);
        return "profile";
    }

    @PostMapping("/update/info")
    public String updateUserInfo(@ModelAttribute("user") User user, RedirectAttributes redirectAttributes) {
        userService.updateUserInfo(user);
        redirectAttributes.addFlashAttribute("successMessage", "Profile information updated successfully!");
        return "redirect:/profile";
    }

    @PostMapping("/update/password")
    public String updateUserPassword(Authentication authentication,
                                     @RequestParam("currentPassword") String currentPassword,
                                     @RequestParam("newPassword") String newPassword,
                                     @RequestParam("confirmPassword") String confirmPassword,
                                     RedirectAttributes redirectAttributes) {

        if (!newPassword.equals(confirmPassword)) {
            redirectAttributes.addFlashAttribute("errorMessage", "New passwords do not match.");
            return "redirect:/profile";
        }

        String email = authentication.getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        try {
            userService.changeUserPassword(user, currentPassword, newPassword);
            redirectAttributes.addFlashAttribute("successMessage", "Password changed successfully!");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }

        return "redirect:/profile";
    }

    @DeleteMapping("/delete")
    public String deleteAccount(Authentication authentication, HttpServletRequest request, RedirectAttributes redirectAttributes) {
        String email = authentication.getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        if (!"ROLE_PLAYER".equals(user.getRole())) {
            redirectAttributes.addFlashAttribute("errorMessage", "Only player accounts can be deleted via this page.");
            return "redirect:/profile";
        }

        try {
            userService.deletePlayerAccount(user);
            new SecurityContextLogoutHandler().logout(request, null, null);
            return "redirect:/login?deleted";
        } catch (IllegalStateException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/profile";
        }
    }
}
