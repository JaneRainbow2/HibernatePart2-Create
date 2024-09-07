package com.softserve.itacademy.service;

import com.softserve.itacademy.model.ToDo;

import java.util.List;
import java.util.Optional;

public interface ToDoService {
    ToDo create(ToDo todo);

    ToDo readById(long id);

    ToDo update(ToDo todo);

    void delete(long id);

    List<ToDo> getByUserId(long userId);

    List<ToDo> getAll();
}
