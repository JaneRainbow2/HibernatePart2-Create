package com.softserve.itacademy.repository;

import com.softserve.itacademy.model.State;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StateRepository extends JpaRepository<State, Long> {

    // Method for retrieving a State by name
    Optional<State> findByName(String name);

    // Method for retrieving all State objects sorted by name
    List<State> findAllByOrderByNameAsc();
}