package com.softserve.itacademy.model;

import jakarta.validation.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class ToDoTests {

    private static Validator validator;
    private static ToDo validToDo;

    @BeforeAll
    static void init() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();

        validToDo = new ToDo();
        validToDo.setTitle("Valid ToDo");
        validToDo.setCreatedAt(LocalDateTime.now());
        validToDo.setOwner(new User());
    }


    @Test
    void createValidToDo() {
        Set<ConstraintViolation<ToDo>> violations = validator.validate(validToDo);
        assertEquals(0, violations.size(), "ToDo should be valid, but validation failed");
    }


    @ParameterizedTest
    @MethodSource("provideInvalidTitles")
    void constraintViolationInvalidTitle(String title, String expectedMessage) {
        ToDo toDo = new ToDo();
        toDo.setTitle(title);
        toDo.setCreatedAt(LocalDateTime.now());
        toDo.setOwner(new User());

        Set<ConstraintViolation<ToDo>> violations = validator.validate(toDo);
        assertEquals(1, violations.size(), "There should be one violation for title");
        assertEquals(expectedMessage, violations.iterator().next().getMessage());
    }

    private static Stream<Arguments> provideInvalidTitles() {
        return Stream.of(
                Arguments.of("", "The 'title' cannot be empty"),
                Arguments.of("  ", "The 'title' cannot be empty")
        );
    }


    @Test
    void constraintViolationMissingCreatedAt() {
        ToDo toDo = new ToDo();
        toDo.setTitle("Test ToDo");
        toDo.setOwner(new User());

        Set<ConstraintViolation<ToDo>> violations = validator.validate(toDo);
        assertEquals(1, violations.size(), "There should be one violation for missing createdAt");
        assertEquals("CreatedAt must not be null", violations.iterator().next().getMessage());
    }


    @Test
    void constraintViolationMissingOwner() {
        ToDo toDo = new ToDo();
        toDo.setTitle("Test ToDo");
        toDo.setCreatedAt(LocalDateTime.now());

        Set<ConstraintViolation<ToDo>> violations = validator.validate(toDo);
        assertEquals(1, violations.size(), "There should be one violation for missing owner");
        assertEquals("Owner must not be null", violations.iterator().next().getMessage());
    }


    @Test
    void createToDoWithValidData() {
        ToDo toDo = new ToDo();
        toDo.setTitle("Valid ToDo");
        toDo.setCreatedAt(LocalDateTime.now());
        toDo.setOwner(new User());

        Set<ConstraintViolation<ToDo>> violations = validator.validate(toDo);
        assertEquals(0, violations.size(), "ToDo should be valid with proper title, createdAt, and owner");
    }


    @Test
    void createToDoWithInvalidData() {
        ToDo toDo = new ToDo();
        toDo.setTitle("");
        toDo.setCreatedAt(null);
        toDo.setOwner(null);

        Set<ConstraintViolation<ToDo>> violations = validator.validate(toDo);
        assertEquals(3, violations.size(), "There should be three violations: title, createdAt, and owner");
    }
}
