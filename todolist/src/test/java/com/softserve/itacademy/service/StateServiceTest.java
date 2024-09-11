package com.softserve.itacademy.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import com.softserve.itacademy.model.State;
import com.softserve.itacademy.repository.StateRepository;
import com.softserve.itacademy.service.impl.StateServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class StateServiceTest {

    @Mock
    private StateRepository stateRepository;

    @InjectMocks
    private StateServiceImpl stateService;

    private State state;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        state = new State();
        state.setId(1L);
        state.setName("Active");
        // Initialize other fields if necessary
    }

    @Test
    public void testCreate() {
        when(stateRepository.save(state)).thenReturn(state);
        State createdState = stateService.create(state);
        assertNotNull(createdState);
        assertEquals(state.getName(), createdState.getName());
        verify(stateRepository, times(1)).save(state);
    }

    @Test
    public void testCreateWithNullState() {
        assertThrows(IllegalArgumentException.class, () -> stateService.create(null));
    }

    @Test
    public void testReadById() {
        when(stateRepository.findById(1L)).thenReturn(Optional.of(state));
        State foundState = stateService.readById(1L);
        assertNotNull(foundState);
        assertEquals(state.getName(), foundState.getName());
        verify(stateRepository, times(1)).findById(1L);
    }

    @Test
    public void testReadByIdInvalidId() {
        assertThrows(IllegalArgumentException.class, () -> stateService.readById(0L));
    }

    @Test
    public void testReadByIdNotFound() {
        when(stateRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> stateService.readById(1L));
    }

    @Test
    public void testUpdate() {
        when(stateRepository.existsById(1L)).thenReturn(true);
        when(stateRepository.save(state)).thenReturn(state);
        State updatedState = stateService.update(state);
        assertNotNull(updatedState);
        assertEquals(state.getName(), updatedState.getName());
        verify(stateRepository, times(1)).save(state);
    }

    @Test
    public void testUpdateWithNullState() {
        assertThrows(IllegalArgumentException.class, () -> stateService.update(null));
    }

    @Test
    public void testUpdateStateNotFound() {
        when(stateRepository.existsById(1L)).thenReturn(false);
        assertThrows(EntityNotFoundException.class, () -> stateService.update(state));
    }

    @Test
    public void testDelete() {
        when(stateRepository.existsById(1L)).thenReturn(true);
        doNothing().when(stateRepository).deleteById(1L);
        stateService.delete(1L);
        verify(stateRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testDeleteWithInvalidId() {
        assertThrows(IllegalArgumentException.class, () -> stateService.delete(0L));
    }

    @Test
    public void testDeleteStateNotFound() {
        when(stateRepository.existsById(1L)).thenReturn(false);
        assertThrows(EntityNotFoundException.class, () -> stateService.delete(1L));
    }

    @Test
    public void testGetAll() {
        when(stateRepository.findAll()).thenReturn(Collections.singletonList(state));
        List<State> states = stateService.getAll();
        assertFalse(states.isEmpty());
        assertEquals(1, states.size());
        assertEquals(state.getName(), states.get(0).getName());
    }

    @Test
    public void testGetAllNoStates() {
        when(stateRepository.findAll()).thenReturn(Collections.emptyList());
        assertThrows(EntityNotFoundException.class, () -> stateService.getAll());
    }

    @Test
    public void testGetByName() {
        when(stateRepository.findByName("Active")).thenReturn(Optional.of(state));
        State foundState = stateService.getByName("Active");
        assertNotNull(foundState);
        assertEquals(state.getName(), foundState.getName());
        verify(stateRepository, times(1)).findByName("Active");
    }

    @Test
    public void testGetByNameWithNullOrEmptyName() {
        assertThrows(IllegalArgumentException.class, () -> stateService.getByName(null));
        assertThrows(IllegalArgumentException.class, () -> stateService.getByName(""));
    }

    @Test
    public void testGetByNameNotFound() {
        when(stateRepository.findByName("Active")).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> stateService.getByName("Active"));
    }
}
