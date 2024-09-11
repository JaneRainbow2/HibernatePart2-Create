package com.softserve.itacademy.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import com.softserve.itacademy.model.ToDo;
import com.softserve.itacademy.repository.ToDoRepository;
import com.softserve.itacademy.service.impl.ToDoServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


public class ToDoServiceTest {

    @Mock
    private ToDoRepository toDoRepository;

    @InjectMocks
    private ToDoServiceImpl toDoService;

    private ToDo todo;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        todo = new ToDo();
        todo.setId(1L);
        todo.setTitle("Test ToDo");
        todo.setCreatedAt(LocalDateTime.now());
    }

    @Test
    public void testCreate() {
        when(toDoRepository.save(todo)).thenReturn(todo);
        ToDo createdTodo = toDoService.create(todo);
        assertNotNull(createdTodo);
        assertEquals(todo.getTitle(), createdTodo.getTitle());
        verify(toDoRepository, times(1)).save(todo);
    }

    @Test
    public void testCreateWithNullToDo() {
        assertThrows(IllegalArgumentException.class, () -> toDoService.create(null));
    }

    @Test
    public void testReadById() {
        when(toDoRepository.findById(1L)).thenReturn(Optional.of(todo));
        ToDo foundTodo = toDoService.readById(1L);
        assertNotNull(foundTodo);
        assertEquals(todo.getTitle(), foundTodo.getTitle());
        verify(toDoRepository, times(1)).findById(1L);
    }

    @Test
    public void testReadByIdNotFound() {
        when(toDoRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> toDoService.readById(1L));
    }

    @Test
    public void testUpdate() {
        when(toDoRepository.existsById(1L)).thenReturn(true);
        when(toDoRepository.save(todo)).thenReturn(todo);
        ToDo updatedTodo = toDoService.update(todo);
        assertNotNull(updatedTodo);
        assertEquals(todo.getTitle(), updatedTodo.getTitle());
        verify(toDoRepository, times(1)).save(todo);
    }

    @Test
    public void testUpdateWithNullToDo() {
        assertThrows(IllegalArgumentException.class, () -> toDoService.update(null));
    }

    @Test
    public void testUpdateToDoNotFound() {
        when(toDoRepository.existsById(1L)).thenReturn(false);
        assertThrows(EntityNotFoundException.class, () -> toDoService.update(todo));
    }

    @Test
    public void testDelete() {
        when(toDoRepository.existsById(1L)).thenReturn(true);
        doNothing().when(toDoRepository).deleteById(1L);
        toDoService.delete(1L);
        verify(toDoRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testDeleteToDoNotFound() {
        when(toDoRepository.existsById(1L)).thenReturn(false);
        assertThrows(EntityNotFoundException.class, () -> toDoService.delete(1L));
    }

    @Test
    public void testGetAll() {
        when(toDoRepository.findAll()).thenReturn(Collections.singletonList(todo));
        List<ToDo> todos = toDoService.getAll();
        assertFalse(todos.isEmpty());
        assertEquals(1, todos.size());
        assertEquals(todo.getTitle(), todos.get(0).getTitle());
    }

    @Test
    public void testGetAllNoToDos() {
        when(toDoRepository.findAll()).thenReturn(Collections.emptyList());
        assertThrows(EntityNotFoundException.class, () -> toDoService.getAll());
    }

    @Test
    public void testGetByUserId() {
        when(toDoRepository.findByUserId(1L)).thenReturn(Collections.singletonList(todo));
        List<ToDo> todos = toDoService.getByUserId(1L);
        assertFalse(todos.isEmpty());
        assertEquals(1, todos.size());
        assertEquals(todo.getTitle(), todos.get(0).getTitle());
    }

    @Test
    public void testGetByUserIdNoToDos() {
        when(toDoRepository.findByUserId(1L)).thenReturn(Collections.emptyList());
        assertThrows(EntityNotFoundException.class, () -> toDoService.getByUserId(1L));
    }
}