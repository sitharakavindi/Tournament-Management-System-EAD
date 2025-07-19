package com.example.TournamentRegistration.repository;

import com.example.TournamentRegistration.model.Tournament;
import com.example.TournamentRegistration.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TournamentRepository extends JpaRepository<Tournament, Long> {

    /**
     * Finds all tournaments created by a specific organizer.
     * The method name tells Spring Data JPA to create a query based on the 'organizer' field in the Tournament entity.
     * @param organizer The user who is the organizer.
     * @return A list of tournaments.
     */
    List<Tournament> findByOrganizer(User organizer);
}
