package com.example.TournamentRegistration.service;

import com.example.TournamentRegistration.dto.UserRegistrationDto;
import com.example.TournamentRegistration.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    User saveOrganizer(UserRegistrationDto registrationDto);

    User savePlayer(UserRegistrationDto registrationDto);

    User updateUserInfo(User user);

    void changeUserPassword(User user, String currentPassword, String newPassword) throws IllegalArgumentException;

    /**
     * Deletes a player's account after checking for upcoming tournament registrations.
     * @param player The player account to be deleted.
     * @throws IllegalStateException if the player is registered for any upcoming tournaments.
     */
    void deletePlayerAccount(User player) throws IllegalStateException;
}
