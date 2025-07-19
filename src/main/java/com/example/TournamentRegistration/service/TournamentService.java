package com.example.TournamentRegistration.service;

import com.example.TournamentRegistration.model.Tournament;
import com.example.TournamentRegistration.model.User;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface TournamentService {

    /**
     * Finds all tournaments created by a specific organizer.
     * @param organizer The user who is the organizer.
     * @return A list of tournaments.
     */
    List<Tournament> findTournamentsByOrganizer(User organizer);

    /**
     * Saves a tournament (either new or existing).
     * @param tournament The tournament object to save.
     * @return The saved tournament.
     */
    Tournament saveTournament(Tournament tournament);

    /**
     * Finds a tournament by its ID.
     * @param id The ID of the tournament.
     * @return An Optional containing the tournament if found.
     */
    Optional<Tournament> findTournamentById(Long id);

    /**
     * Deletes a tournament by its ID.
     * @param id The ID of the tournament to delete.
     */
    void deleteTournamentById(Long id);

    /**
     * Finds all tournaments available in the system.
     * @return A list of all tournaments.
     */
    List<Tournament> findAllTournaments();

    /**
     * Calculates dashboard statistics for a given organizer.
     * @param organizer The user (organizer).
     * @return A Map containing keys like "activeTournaments", "completedTournaments", "totalPlayers".
     */
    Map<String, Long> getOrganizerDashboardStats(User organizer);
}
