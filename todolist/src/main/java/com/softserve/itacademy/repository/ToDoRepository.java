package com.softserve.itacademy.repository;

import com.softserve.itacademy.model.ToDo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ToDoRepository extends JpaRepository<ToDo, Long> {
// Implement a method that retrieves a list of ToDo objects where the user is either the owner
// or a collaborator. The method should accept a long value representing the userId as a parameter.
// It should return all tasks from the 'todos' table where the user is the owner (owner_id) or
// a collaborator (collaborator_id) in the 'todo_collaborator' table.
}
