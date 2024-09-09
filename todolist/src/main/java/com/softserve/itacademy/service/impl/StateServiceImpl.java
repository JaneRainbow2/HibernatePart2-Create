package com.softserve.itacademy.service.impl;

import com.softserve.itacademy.model.State;
import com.softserve.itacademy.repository.StateRepository;
import com.softserve.itacademy.service.StateService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StateServiceImpl implements StateService {

    @Autowired
    private StateRepository stateRepository;

    public StateServiceImpl() {
    }

    @Override
    public State create(State state) {
        if (state == null) {
            throw new IllegalArgumentException("State cannot be null");
        }
        return stateRepository.save(state);
    }

    @Override
    public State readById(long id) {
        if (id <= 0) {
            throw new IllegalArgumentException("Invalid state ID");
        }
        return stateRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("State with id " + id + " not found"));
    }

    @Override
    public State update(State state) {
        if (state == null) {
            throw new IllegalArgumentException("State cannot be null");
        }
        if (stateRepository.existsById(state.getId())) {
            return stateRepository.save(state);
        } else {
            throw new EntityNotFoundException("State with id " + state.getId() + " not found");
        }
    }

    @Override
    public void delete(long id) {
        if (id <= 0) {
            throw new IllegalArgumentException("Invalid state ID");
        }
        if (stateRepository.existsById(id)) {
            stateRepository.deleteById(id);
        } else {
            throw new EntityNotFoundException("State with id " + id + " not found");
        }
    }

    @Override
    public List<State> getAll() {
        List<State> states = stateRepository.findAll();
        if (states.isEmpty()) {
            throw new EntityNotFoundException("No states found");
        }
        return states;
    }

    @Override
    public State getByName(String name) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("State name cannot be null or empty");
        }
        return stateRepository.findByName(name)
                .orElseThrow(() -> new EntityNotFoundException("State with name " + name + " not found"));
    }
}