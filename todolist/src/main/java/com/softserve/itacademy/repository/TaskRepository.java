package com.softserve.itacademy.repository;

import com.softserve.itacademy.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

// Implement a method that retrieves a list of Task objects associated with a specific todo_id.
// The method should accept a long value representing the todo_id as a parameter and return
// all tasks from the 'tasks' table that are linked to this todo_id.

}
