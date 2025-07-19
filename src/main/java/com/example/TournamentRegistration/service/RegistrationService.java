package com.example.TournamentRegistration.service;

import com.example.TournamentRegistration.model.Registration;
import com.example.TournamentRegistration.model.Tournament;
import com.example.TournamentRegistration.model.User;

import java.util.List;

public interface RegistrationService {

    /**
     * Registers a player for a specific tournament.
     * It will check if the player is already registered.
     * @param player The user (player) to register.
     * @param tournament The tournament to register for.
     * @return The created Registration object.
     * @throws IllegalStateException if the player is already registered for the tournament.
     */
    Registration registerPlayerForTournament(User player, Tournament tournament) throws IllegalStateException;

    /**
     * Finds all registrations for a specific player.
     * @param player The user (player).
     * @return A list of registrations.
     */
    List<Registration> findRegistrationsByPlayer(User player);

    /**
     * Finds all registrations for a specific tournament.
     * @param tournament The tournament.
     * @return A list of registrations.
     */
    List<Registration> findRegistrationsByTournament(Tournament tournament);
}
