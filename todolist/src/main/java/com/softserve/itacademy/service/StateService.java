package com.softserve.itacademy.service;

import com.softserve.itacademy.model.State;

import java.util.List;
import java.util.Optional;

public interface StateService {
    State create(State state);

    State readById(long id);

    State update(State state);

    void delete(long id);

    State getByName(String name);

    List<State> getAll();
}
