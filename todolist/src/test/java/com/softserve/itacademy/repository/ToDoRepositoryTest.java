package com.softserve.itacademy.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import com.softserve.itacademy.model.ToDo;
import com.softserve.itacademy.model.User;
import com.softserve.itacademy.model.UserRole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class ToDoRepositoryTest {

    @Autowired
    private ToDoRepository toDoRepository;

    @Autowired
    private UserRepository userRepository; // Предполагается, что у вас есть UserRepository

    private User owner;
    private User collaborator;
    private ToDo todo1;
    private ToDo todo2;
    private ToDo todo3;

    @BeforeEach
    public void setUp() {
        owner = new User();
        owner.setFirstName("John");
        owner.setLastName("Doe");
        owner.setEmail("john.doe@example.com");
        owner.setPassword("Password123");
        owner.setRole(UserRole.USER);
        userRepository.save(owner);

        collaborator = new User();
        collaborator.setFirstName("Jane");
        collaborator.setLastName("Smith");
        collaborator.setEmail("jane.smith@example.com");
        collaborator.setPassword("Password123");
        collaborator.setRole(UserRole.USER);
        userRepository.save(collaborator);

        todo1 = new ToDo();
        todo1.setTitle("ToDo 1");
        todo1.setCreatedAt(LocalDateTime.now());
        todo1.setOwner(owner);
        todo1.setCollaborators(Arrays.asList(collaborator));
        toDoRepository.save(todo1);

        todo2 = new ToDo();
        todo2.setTitle("ToDo 2");
        todo2.setCreatedAt(LocalDateTime.now());
        todo2.setOwner(collaborator);
        toDoRepository.save(todo2);

        todo3 = new ToDo();
        todo3.setTitle("ToDo 3");
        todo3.setCreatedAt(LocalDateTime.now());
        todo3.setOwner(owner);
        toDoRepository.save(todo3);
    }

    @Test
    public void testFindByUserIdAsOwner() {
        List<ToDo> todos = toDoRepository.findByUserId(owner.getId());
        assertThat(todos).containsExactlyInAnyOrder(todo1, todo3);
    }

    @Test
    public void testFindByUserIdAsCollaborator() {
        List<ToDo> todos = toDoRepository.findByUserId(collaborator.getId());
        assertThat(todos).containsExactlyInAnyOrder(todo1, todo2);
    }

    @Test
    public void testFindByUserIdNoToDos() {
        List<ToDo> todos = toDoRepository.findByUserId(999L); // ID, которого нет в базе
        assertThat(todos).isEmpty();
    }
}