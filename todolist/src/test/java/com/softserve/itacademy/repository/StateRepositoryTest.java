package com.softserve.itacademy.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.softserve.itacademy.model.State;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

@DataJpaTest
public class StateRepositoryTest {

    @Autowired
    private StateRepository stateRepository;

    private State state;

    @BeforeEach
    public void setUp() {
        state = new State();
        state.setName("Active");
        stateRepository.save(state);
    }

    @Test
    public void testFindByName() {
        Optional<State> foundState = stateRepository.findByName("Active");
        assertTrue(foundState.isPresent(), "State should be found");
        assertThat(foundState.get().getName()).isEqualTo("Active");
    }

    @Test
    public void testFindAllByOrderByNameAsc() {
        State anotherState = new State();
        anotherState.setName("Inactive");
        stateRepository.save(anotherState);

        List<State> states = stateRepository.findAllByOrderByNameAsc();
        assertThat(states).hasSize(2);
        assertThat(states.get(0).getName()).isEqualTo("Active");
        assertThat(states.get(1).getName()).isEqualTo("Inactive");
    }
}