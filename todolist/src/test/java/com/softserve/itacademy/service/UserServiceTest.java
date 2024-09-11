package com.softserve.itacademy.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

import com.softserve.itacademy.model.User;
import com.softserve.itacademy.model.UserRole;
import com.softserve.itacademy.repository.UserRepository;
import com.softserve.itacademy.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import jakarta.persistence.EntityNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

public class UserServiceTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    private User user;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        user = new User();
        user.setId(1L);
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setEmail("john.doe@example.com");
        user.setPassword("password123");
        user.setRole(UserRole.USER);
    }

    @Test
    public void testCreateUser() {
        when(userRepository.save(any(User.class))).thenReturn(user);

        User createdUser = userService.create(user);

        assertNotNull(createdUser);
        assertEquals(user.getId(), createdUser.getId());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    public void testCreateUserNull() {
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            userService.create(null);
        });

        assertEquals("User cannot be null", thrown.getMessage());
    }

    @Test
    public void testReadById() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));

        User foundUser = userService.readById(1L);

        assertNotNull(foundUser);
        assertEquals(user.getId(), foundUser.getId());
        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    public void testReadByIdNotFound() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

        EntityNotFoundException thrown = assertThrows(EntityNotFoundException.class, () -> {
            userService.readById(1L);
        });

        assertEquals("User with id 1 not found", thrown.getMessage());
    }

    @Test
    public void testUpdateUser() {
        when(userRepository.existsById(anyLong())).thenReturn(true);
        when(userRepository.save(any(User.class))).thenReturn(user);

        User updatedUser = userService.update(user);

        assertNotNull(updatedUser);
        assertEquals(user.getId(), updatedUser.getId());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    public void testUpdateUserNotFound() {
        when(userRepository.existsById(anyLong())).thenReturn(false);

        EntityNotFoundException thrown = assertThrows(EntityNotFoundException.class, () -> {
            userService.update(user);
        });

        assertEquals("User with id 1 not found", thrown.getMessage());
    }

    @Test
    public void testDeleteUser() {
        when(userRepository.existsById(anyLong())).thenReturn(true);

        userService.delete(1L);

        verify(userRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testDeleteUserNotFound() {
        when(userRepository.existsById(anyLong())).thenReturn(false);

        EntityNotFoundException thrown = assertThrows(EntityNotFoundException.class, () -> {
            userService.delete(1L);
        });

        assertEquals("User with id 1 not found", thrown.getMessage());
    }

    @Test
    public void testGetAllUsers() {
        when(userRepository.findAll()).thenReturn(Collections.singletonList(user));

        List<User> users = userService.getAll();

        assertFalse(users.isEmpty());
        assertEquals(1, users.size());
        assertEquals(user.getId(), users.get(0).getId());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    public void testGetAllUsersNoUsers() {
        when(userRepository.findAll()).thenReturn(Collections.emptyList());

        EntityNotFoundException thrown = assertThrows(EntityNotFoundException.class, () -> {
            userService.getAll();
        });

        assertEquals("No users found", thrown.getMessage());
    }

    @Test
    public void testFindByEmail() {
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));

        Optional<User> foundUser = userService.findByEmail("john.doe@example.com");

        assertTrue(foundUser.isPresent());
        assertEquals(user.getId(), foundUser.get().getId());
    }

    @Test
    public void testFindByEmailNotFound() {
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());

        EntityNotFoundException thrown = assertThrows(EntityNotFoundException.class, () -> {
            userService.findByEmail("john.doe@example.com");
        });

        assertEquals("User with email john.doe@example.com not found", thrown.getMessage());
    }

    @Test
    public void testFindByEmailNull() {
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            userService.findByEmail(null);
        });

        assertEquals("Email cannot be null or empty", thrown.getMessage());
    }

    @Test
    public void testFindByEmailEmpty() {
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            userService.findByEmail("");
        });

        assertEquals("Email cannot be null or empty", thrown.getMessage());
    }

    @Test
    public void testFindById() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));

        Optional<User> foundUser = userService.findById(1L);

        assertTrue(foundUser.isPresent());
        assertEquals(user.getId(), foundUser.get().getId());
    }

    @Test
    public void testFindByIdNotFound() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

        EntityNotFoundException thrown = assertThrows(EntityNotFoundException.class, () -> {
            userService.findById(1L);
        });

        assertEquals("User with id 1 not found", thrown.getMessage());
    }
}