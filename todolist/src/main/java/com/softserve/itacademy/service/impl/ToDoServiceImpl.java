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

    @Autowired
    private ToDoRepository toDoRepository;

    public ToDoServiceImpl() {}

    @Override
    public ToDo create(ToDo todo) {
        if (todo == null) {
            throw new IllegalArgumentException("ToDo object cannot be null");
        }
        return toDoRepository.save(todo);
    }

    @Override
    public ToDo readById(long id) {
        return toDoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("ToDo with ID " + id + " not found"));
    }

    @Override
    public ToDo update(ToDo todo) {
        if (todo == null) {
            throw new IllegalArgumentException("ToDo object cannot be null");
        }
        if (!toDoRepository.existsById(todo.getId())) {
            throw new EntityNotFoundException("ToDo with ID " + todo.getId() + " not found for update");
        }
        return toDoRepository.save(todo);
    }

    @Override
    public void delete(long id) {
        if (!toDoRepository.existsById(id)) {
            throw new EntityNotFoundException("ToDo with ID " + id + " not found for deletion");
        }
        toDoRepository.deleteById(id);
    }

    @Override
    public List<ToDo> getAll() {
        List<ToDo> todos = toDoRepository.findAll();
        if (todos.isEmpty()) {
            throw new EntityNotFoundException("No ToDo entries found");
        }
        return todos;
    }

    @Override
    public List<ToDo> getByUserId(long userId) {
        List<ToDo> todos = toDoRepository.findByUserId(userId);
        if (todos.isEmpty()) {
            throw new EntityNotFoundException("No ToDo entries found for User ID " + userId);
        }
        return todos;
    }
}