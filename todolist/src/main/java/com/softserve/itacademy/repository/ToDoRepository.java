package com.softserve.itacademy.repository;

import com.softserve.itacademy.model.ToDo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ToDoRepository extends JpaRepository<ToDo, Long> {

    // Custom query to find ToDo objects where the user is either the owner or a collaborator
    @Query("SELECT t FROM ToDo t LEFT JOIN t.collaborators c WHERE t.owner.id = :userId OR c.id = :userId")
    List<ToDo> findByUserId(long userId);
}