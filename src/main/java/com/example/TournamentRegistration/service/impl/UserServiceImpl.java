package com.example.TournamentRegistration.service.impl;

import com.example.TournamentRegistration.dto.UserRegistrationDto;
import com.example.TournamentRegistration.model.Registration;
import com.example.TournamentRegistration.model.User;
import com.example.TournamentRegistration.repository.RegistrationRepository;
import com.example.TournamentRegistration.repository.UserRepository;
import com.example.TournamentRegistration.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RegistrationRepository registrationRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           RegistrationRepository registrationRepository,
                           @Lazy BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.registrationRepository = registrationRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User saveOrganizer(UserRegistrationDto registrationDto) {

        if (userRepository.findByUsername(registrationDto.getUsername()).isPresent()) {
            throw new IllegalStateException("A user with username '" + registrationDto.getUsername() + "' already exists.");
        }
        if (userRepository.findByEmail(registrationDto.getEmail()).isPresent()) {
            throw new IllegalStateException("A user with email '" + registrationDto.getEmail() + "' already exists.");
        }
        User user = new User();
        user.setUsername(registrationDto.getUsername());
        user.setName(registrationDto.getName());
        user.setEmail(registrationDto.getEmail());
        user.setPassword(passwordEncoder.encode(registrationDto.getPassword()));
        user.setRole("ROLE_ORGANIZER");
        return userRepository.save(user);
    }

    @Override
    public User savePlayer(UserRegistrationDto registrationDto) {
        if (userRepository.findByUsername(registrationDto.getUsername()).isPresent()) {
            throw new IllegalStateException("A user with username '" + registrationDto.getUsername() + "' already exists.");
        }
        if (userRepository.findByEmail(registrationDto.getEmail()).isPresent()) {
            throw new IllegalStateException("A user with email '" + registrationDto.getEmail() + "' already exists.");
        }
        User user = new User();
        user.setUsername(registrationDto.getUsername());
        user.setName(registrationDto.getName());
        user.setEmail(registrationDto.getEmail());
        user.setPassword(passwordEncoder.encode(registrationDto.getPassword()));
        user.setRole("ROLE_PLAYER");
        return userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(usernameOrEmail)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + usernameOrEmail));
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(), user.getPassword(), mapRolesToAuthorities(user.getRole()));
    }

    @Override
    public User updateUserInfo(User updatedUser) {
        User existingUser = userRepository.findById(updatedUser.getId())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        existingUser.setName(updatedUser.getName());
        return userRepository.save(existingUser);
    }

    @Override
    public void changeUserPassword(User user, String currentPassword, String newPassword) throws IllegalArgumentException {
        if (!passwordEncoder.matches(currentPassword, user.getPassword())) {
            throw new IllegalArgumentException("Incorrect current password.");
        }
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void deletePlayerAccount(User player) throws IllegalStateException {
        List<Registration> registrations = registrationRepository.findAllByPlayer(player);

        boolean hasUpcomingRegistrations = registrations.stream()
                .anyMatch(reg -> !reg.getTournament().getTournamentDate().isBefore(LocalDate.now()));

        if (hasUpcomingRegistrations) {
            throw new IllegalStateException("Cannot delete account. You are registered for upcoming tournaments. Please contact the organizer to unregister first.");
        }

        registrationRepository.deleteAll(registrations);
        userRepository.delete(player);
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(String role) {
        return Collections.singleton(new SimpleGrantedAuthority(role));
    }
}
