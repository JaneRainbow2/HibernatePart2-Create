package com.softserve.itacademy.model;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.boot.test.context.SpringBootTest;

import jakarta.validation.*;
import java.util.Set;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class UserTests {

    private static Validator validator;
    private static User validUser;

    @BeforeAll
    static void init() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();

        validUser = new User();
        validUser.setEmail("valid@gcom.com");
        validUser.setFirstName("Anna-Maria");
        validUser.setLastName("Kovalenko-Bynzar");
        validUser.setPassword("qwQW12!@");
        validUser.setRole(UserRole.USER);
    }

    @Test
    void createValidUser() {
        Set<ConstraintViolation<User>> violations = validator.validate(validUser);
        assertEquals(0, violations.size());
    }

    @ParameterizedTest
    @MethodSource("provideInvalidFirstNames")
    void constraintViolationInvalidFirstName(String firstName, String expectedMessage) {
        User user = new User();
        user.setFirstName(firstName);
        user.setLastName("Kovalenko-Bynzar");
        user.setEmail("valid@gcom.com");
        user.setPassword("qwQW12!@");
        user.setRole(UserRole.USER);

        Set<ConstraintViolation<User>> violations = validator.validate(user);

        for (ConstraintViolation<User> violation : violations) {
            System.out.println("Property: " + violation.getPropertyPath() + ", Message: " + violation.getMessage());
        }

        assertEquals(1, violations.size());
        assertEquals(expectedMessage, violations.iterator().next().getMessage());
    }
    private static Stream<Arguments> provideInvalidFirstNames() {
        return Stream.of(
                Arguments.of("anna", "First name must start with a capital letter followed by lowercase letters, optionally separated by a hyphen"),
                Arguments.of("Anna1", "First name must start with a capital letter followed by lowercase letters, optionally separated by a hyphen"),
                Arguments.of("ANNA", "First name must start with a capital letter followed by lowercase letters, optionally separated by a hyphen"),
                Arguments.of("", "First name must start with a capital letter followed by lowercase letters, optionally separated by a hyphen")
        );
    }

    @ParameterizedTest
    @MethodSource("provideInvalidLastNames")
    void constraintViolationInvalidLastName(String lastName, String expectedMessage) {
        User user = new User();
        user.setFirstName("Anna-Maria");
        user.setLastName(lastName);
        user.setEmail("valid@gcom.com");
        user.setPassword("qwQW12!@");
        user.setRole(UserRole.USER);

        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertEquals(1, violations.size());
        assertEquals(expectedMessage, violations.iterator().next().getMessage());
    }

    private static Stream<Arguments> provideInvalidLastNames() {
        return Stream.of(
                Arguments.of("bynzar", "Last name must start with a capital letter followed by lowercase letters, optionally separated by a hyphen"),
                Arguments.of("Bynzar1", "Last name must start with a capital letter followed by lowercase letters, optionally separated by a hyphen"),
                Arguments.of("BYNZAR", "Last name must start with a capital letter followed by lowercase letters, optionally separated by a hyphen"),
                Arguments.of("", "Last name must start with a capital letter followed by lowercase letters, optionally separated by a hyphen")
        );
    }

    @ParameterizedTest
    @MethodSource("provideInvalidEmails")
    void constraintViolationInvalidEmail(String email, String expectedMessage) {
        User user = new User();
        user.setFirstName("Anna-Maria");
        user.setLastName("Kovalenko-Bynzar");
        user.setEmail(email);
        user.setPassword("qwQW12!@");
        user.setRole(UserRole.USER);

        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertEquals(1, violations.size());
        assertEquals(expectedMessage, violations.iterator().next().getMessage());
    }

    private static Stream<Arguments> provideInvalidEmails() {
        return Stream.of(
                Arguments.of("invalidEmail", "Must be a valid e-mail address"),
                Arguments.of("email@", "Must be a valid e-mail address"),
                Arguments.of("invalid.com", "Must be a valid e-mail address"),
                Arguments.of("", "Must be a valid e-mail address")
        );
    }

    @ParameterizedTest
    @MethodSource("provideInvalidPasswords")
    void constraintViolationInvalidPassword(String password, String expectedMessage) {
        User user = new User();
        user.setFirstName("Anna-Maria");
        user.setLastName("Kovalenko-Bynzar");
        user.setEmail("valid@gcom.com");
        user.setPassword(password);
        user.setRole(UserRole.USER);

        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertEquals(1, violations.size());
        assertEquals(expectedMessage, violations.iterator().next().getMessage());
    }

    private static Stream<Arguments> provideInvalidPasswords() {
        return Stream.of(
                Arguments.of("qwe123", "Password must be at least 8 characters long and contain at least one letter, one number, and can include special characters"),
                Arguments.of("123456", "Password must be at least 8 characters long and contain at least one letter, one number, and can include special characters"),
                Arguments.of("abcdef", "Password must be at least 8 characters long and contain at least one letter, one number, and can include special characters"),
                Arguments.of("", "Password must be at least 8 characters long and contain at least one letter, one number, and can include special characters")
        );
    }
}
