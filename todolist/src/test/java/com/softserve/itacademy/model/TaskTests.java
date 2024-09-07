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


public class TaskTests {

    private static Validator validator;
    private static Task validTask;

    @BeforeAll
    static void init() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();

        validTask = new Task();
        validTask.setName("Complete Assignment");
        validTask.setPriority(TaskPriority.HIGH);
        validTask.setTodo(new ToDo());
        validTask.setState(new State());
    }

    @Test
    void createValidTask() {
        Set<ConstraintViolation<Task>> violations = validator.validate(validTask);
        assertEquals(0, violations.size(), "Task should be valid, but validation failed");
    }

    @Test
    void constraintViolationInvalidName() {
        Task task = new Task();

        task.setName("");
        task.setPriority(TaskPriority.MEDIUM);
        task.setTodo(new ToDo());
        task.setState(new State());
        Set<ConstraintViolation<Task>> violations = validator.validate(task);

        violations.forEach(violation ->
                System.out.println("Field: " + violation.getPropertyPath() + ", Message: " + violation.getMessage())
        );

        assertEquals(1, violations.size(), "There should be one violation for name");
        assertEquals("The 'name' cannot be empty", violations.iterator().next().getMessage());
    }

    @Test
    void constraintViolationMissingToDo() {
        Task task = new Task();
        task.setName("Valid Task");
        task.setPriority(TaskPriority.HIGH);
        task.setTodo(null);
        task.setState(new State());

        Set<ConstraintViolation<Task>> violations = validator.validate(task);
        assertEquals(1, violations.size(), "There should be one violation for missing todo");
    }

    @Test
    void constraintViolationMissingState() {
        Task task = new Task();
        task.setName("Valid Task");
        task.setPriority(TaskPriority.HIGH);
        task.setTodo(new ToDo());
        task.setState(null);

        Set<ConstraintViolation<Task>> violations = validator.validate(task);
        assertEquals(1, violations.size(), "There should be one violation for missing state");
    }

    @Test
    void createTaskWithValidData() {
        Task task = new Task();
        task.setName("Complete Homework");
        task.setPriority(TaskPriority.MEDIUM);
        task.setTodo(new ToDo());
        task.setState(new State());

        Set<ConstraintViolation<Task>> violations = validator.validate(task);
        assertEquals(0, violations.size(), "Task should be valid with proper data");
    }

    @Test
    void createTaskWithInvalidData() {
        Task task = new Task();
        task.setName("");
        task.setPriority(null);
        task.setTodo(null);
        task.setState(null);

        Set<ConstraintViolation<Task>> violations = validator.validate(task);
        assertEquals(4, violations.size(), "There should be four violations: name, priority, todo, and state");
    }
}
