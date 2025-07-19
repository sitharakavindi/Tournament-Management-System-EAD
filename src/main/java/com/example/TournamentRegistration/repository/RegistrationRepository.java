package com.example.TournamentRegistration.repository;

import com.example.TournamentRegistration.model.Registration;
import com.example.TournamentRegistration.model.Tournament;
import com.example.TournamentRegistration.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RegistrationRepository extends JpaRepository<Registration, Long> {

    /**
     * Finds a specific registration by player and tournament.
     * Useful to check if a player is already registered for a specific tournament.
     * @param player The user who is the player
     * @param tournament The tournament
     * @return an Optional containing the registration if it exists
     */
    Optional<Registration> findByPlayerAndTournament(User player, Tournament tournament);

    /**
     * Finds all registrations for a specific player.
     * Useful for a player to see all their registered tournaments.
     * @param player The user who is the player
     * @return A list of registrations
     */
    List<Registration> findAllByPlayer(User player);

    /**
     * Finds all registrations for a specific tournament.
     * Useful for an organizer to see all players registered for their tournament.
     * @param tournament The tournament
     * @return A list of registrations
     */
    List<Registration> findAllByTournament(Tournament tournament);
}
