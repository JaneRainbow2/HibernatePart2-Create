package com.softserve.itacademy.service.impl;

import com.softserve.itacademy.model.ToDo;
import com.softserve.itacademy.repository.ToDoRepository;
import com.softserve.itacademy.service.ToDoService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class ToDoServiceImpl implements ToDoService {

    @Override
    public ToDo create(ToDo todo) {
        return null;
    }

    @Override
    public ToDo readById(long id) {

        return null;
    }

    @Override
    public ToDo update(ToDo todo) {

        return null;
    }

    @Override
    public void delete(long id) {

    }

    @Override
    public List<ToDo> getAll() {

        return Collections.emptyList();
    }

    @Override
    public List<ToDo> getByUserId(long userId) {

        return Collections.emptyList();
    }
}
