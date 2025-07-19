package com.example.TournamentRegistration.service.impl;

import com.example.TournamentRegistration.model.Registration;
import com.example.TournamentRegistration.model.Tournament;
import com.example.TournamentRegistration.model.User;
import com.example.TournamentRegistration.repository.RegistrationRepository;
import com.example.TournamentRegistration.service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class RegistrationServiceImpl implements RegistrationService {

    private final RegistrationRepository registrationRepository;

    @Autowired
    public RegistrationServiceImpl(RegistrationRepository registrationRepository) {
        this.registrationRepository = registrationRepository;
    }

    @Override
    public Registration registerPlayerForTournament(User player, Tournament tournament) throws IllegalStateException {
        // Check if the player is already registered for this tournament
        registrationRepository.findByPlayerAndTournament(player, tournament).ifPresent(reg -> {
            throw new IllegalStateException("Player is already registered for this tournament.");
        });

        // If not registered, create a new registration
        Registration newRegistration = new Registration();
        newRegistration.setPlayer(player);
        newRegistration.setTournament(tournament);
        newRegistration.setRegistrationDate(LocalDateTime.now());

        return registrationRepository.save(newRegistration);
    }

    @Override
    public List<Registration> findRegistrationsByPlayer(User player) {
        return registrationRepository.findAllByPlayer(player);
    }

    @Override
    public List<Registration> findRegistrationsByTournament(Tournament tournament) {
        return registrationRepository.findAllByTournament(tournament);
    }
}
