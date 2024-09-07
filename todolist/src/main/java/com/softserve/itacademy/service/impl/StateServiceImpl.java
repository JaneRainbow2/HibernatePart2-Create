package com.softserve.itacademy.service.impl;

import com.softserve.itacademy.model.State;
import com.softserve.itacademy.service.StateService;
import org.springframework.stereotype.Service;
import java.util.Collections;
import java.util.List;

@Service
public class StateServiceImpl implements StateService {

    @Override
    public State create(State state) {

        return null;
    }

    @Override
    public State readById(long id) {

        return null;
    }

    @Override
    public State update(State state) {

        return null;
    }

    @Override
    public void delete(long id) {

    }

    @Override
    public List<State> getAll() {

        return Collections.emptyList();
    }

    @Override
    public State getByName(String name) {
        return null;
    }
}
