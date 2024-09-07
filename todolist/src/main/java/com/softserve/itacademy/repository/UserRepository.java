package com.softserve.itacademy.repository;

import com.softserve.itacademy.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
// Implement a method that retrieves a User object based on the email address.
// The method should accept a String value representing the user's email as a parameter.
// It should return an Optional<User>, where the result is either the found User object or empty if no user is found.

}
