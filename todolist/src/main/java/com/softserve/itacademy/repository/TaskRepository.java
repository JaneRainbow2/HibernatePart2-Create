package com.softserve.itacademy.repository;

import com.softserve.itacademy.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    // Method to retrieve a list of Task objects associated with a specific todo_id
    List<Task> findByTodoId(long todoId);
}
