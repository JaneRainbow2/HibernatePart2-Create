package com.softserve.itacademy.model;

import jakarta.validation.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Set;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class StateTests {

    private static Validator validator;
    private static State validState;

    @BeforeAll
    static void init() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();

        validState = new State();
        validState.setName("In Progress");
    }

    @Test
    void createValidState() {
        Set<ConstraintViolation<State>> violations = validator.validate(validState);
        assertEquals(0, violations.size(), "State should be valid, but validation failed");
    }

    @ParameterizedTest
    @MethodSource("provideInvalidNames")
    void constraintViolationInvalidName(String name, String expectedMessage) {
        State state = new State();
        state.setName(name);

        Set<ConstraintViolation<State>> violations = validator.validate(state);
        assertEquals(1, violations.size(), "There should be one violation for name");
        assertEquals(expectedMessage, violations.iterator().next().getMessage());
    }

    private static Stream<Arguments> provideInvalidNames() {
        return Stream.of(
                Arguments.of("", "The 'name' cannot be empty"),
                Arguments.of("   ", "The 'name' cannot be empty")
        );
    }

    @Test
    void constraintViolationMissingName() {
        State state = new State();
        state.setName(null);

        Set<ConstraintViolation<State>> violations = validator.validate(state);
        assertEquals(1, violations.size(), "There should be one violation for missing name");
        assertEquals("The 'name' cannot be empty", violations.iterator().next().getMessage());
    }

    @Test
    void createStateWithValidName() {
        State state = new State();
        state.setName("Completed");

        Set<ConstraintViolation<State>> violations = validator.validate(state);
        assertEquals(0, violations.size(), "State should be valid with proper name");
    }

    @Test
    void createStateWithInvalidData() {
        State state = new State();
        state.setName(""); // Пустий name

        Set<ConstraintViolation<State>> violations = validator.validate(state);
        assertEquals(1, violations.size(), "There should be one violation for empty name");
    }
}
