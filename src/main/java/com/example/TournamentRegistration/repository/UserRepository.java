package com.example.TournamentRegistration.repository;

import com.example.TournamentRegistration.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Finds a user by their email address.
     * This will be used for the login process.
     * @param email the user's email
     * @return an Optional containing the user if found, or empty if not
     */
    Optional<User> findByEmail(String email);

    /**
     * Finds a user by their username.
     * This is useful to check if a username is already taken during registration.
     * @param username the user's username
     * @return an Optional containing the user if found, or empty if not
     */
    Optional<User> findByUsername(String username);

}
