package com.example.TournamentRegistration.service.impl;

import com.example.TournamentRegistration.model.Registration;
import com.example.TournamentRegistration.model.Tournament;
import com.example.TournamentRegistration.model.User;
import com.example.TournamentRegistration.repository.RegistrationRepository;
import com.example.TournamentRegistration.repository.TournamentRepository;
import com.example.TournamentRegistration.service.TournamentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TournamentServiceImpl implements TournamentService {

    private final TournamentRepository tournamentRepository;
    private final RegistrationRepository registrationRepository;

    @Autowired
    public TournamentServiceImpl(TournamentRepository tournamentRepository, RegistrationRepository registrationRepository) {
        this.tournamentRepository = tournamentRepository;
        this.registrationRepository = registrationRepository;
    }

    @Override
    public List<Tournament> findTournamentsByOrganizer(User organizer) {
        return tournamentRepository.findByOrganizer(organizer);
    }

    @Override
    public Tournament saveTournament(Tournament tournament) {
        return tournamentRepository.save(tournament);
    }

    @Override
    public Optional<Tournament> findTournamentById(Long id) {
        return tournamentRepository.findById(id);
    }

    @Override
    public void deleteTournamentById(Long id) {
        tournamentRepository.deleteById(id);
    }

    @Override
    public List<Tournament> findAllTournaments() {
        return tournamentRepository.findAll();
    }

    @Override
    public Map<String, Long> getOrganizerDashboardStats(User organizer) {
        Map<String, Long> stats = new HashMap<>();
        LocalDate today = LocalDate.now();


        List<Tournament> organizerTournaments = tournamentRepository.findByOrganizer(organizer);


        long activeTournaments = organizerTournaments.stream()
                .filter(t -> !t.getTournamentDate().isBefore(today))
                .count();


        long completedTournaments = organizerTournaments.stream()
                .filter(t -> t.getTournamentDate().isBefore(today))
                .count();


        long totalPlayers = organizerTournaments.stream()
                .flatMap(t -> registrationRepository.findAllByTournament(t).stream())
                .map(Registration::getPlayer)
                .distinct()
                .count();

        stats.put("activeTournaments", activeTournaments);
        stats.put("completedTournaments", completedTournaments);
        stats.put("totalPlayers", totalPlayers);

        return stats;
    }
}
