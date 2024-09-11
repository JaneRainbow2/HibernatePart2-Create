package com.softserve.itacademy.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import com.softserve.itacademy.model.State;
import com.softserve.itacademy.model.Task;
import com.softserve.itacademy.model.TaskPriority;
import com.softserve.itacademy.model.ToDo;
import com.softserve.itacademy.repository.TaskRepository;
import com.softserve.itacademy.service.impl.TaskServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskServiceImpl taskService;

    private Task task;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        task = new Task();
        task.setId(1L);
        task.setName("Test Task");
        task.setPriority(TaskPriority.HIGH);
        task.setTodo(new ToDo()); // Set an appropriate ToDo object
        task.setState(new State()); // Set an appropriate State object
    }

    @Test
    public void testCreate() {
        when(taskRepository.save(task)).thenReturn(task);
        Task createdTask = taskService.create(task);
        assertNotNull(createdTask);
        assertEquals(task.getName(), createdTask.getName());
        verify(taskRepository, times(1)).save(task);
    }

    @Test
    public void testCreateWithNullTask() {
        assertThrows(IllegalArgumentException.class, () -> taskService.create(null));
    }

    @Test
    public void testReadById() {
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
        Task foundTask = taskService.readById(1L);
        assertNotNull(foundTask);
        assertEquals(task.getName(), foundTask.getName());
        verify(taskRepository, times(1)).findById(1L);
    }

    @Test
    public void testReadByIdNotFound() {
        when(taskRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> taskService.readById(1L));
    }

    @Test
    public void testUpdate() {
        when(taskRepository.existsById(1L)).thenReturn(true);
        when(taskRepository.save(task)).thenReturn(task);
        Task updatedTask = taskService.update(task);
        assertNotNull(updatedTask);
        assertEquals(task.getName(), updatedTask.getName());
        verify(taskRepository, times(1)).save(task);
    }

    @Test
    public void testUpdateWithNullTask() {
        assertThrows(IllegalArgumentException.class, () -> taskService.update(null));
    }

    @Test
    public void testUpdateTaskNotFound() {
        when(taskRepository.existsById(1L)).thenReturn(false);
        assertThrows(EntityNotFoundException.class, () -> taskService.update(task));
    }

    @Test
    public void testDelete() {
        when(taskRepository.existsById(1L)).thenReturn(true);
        doNothing().when(taskRepository).deleteById(1L);
        taskService.delete(1L);
        verify(taskRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testDeleteTaskNotFound() {
        when(taskRepository.existsById(1L)).thenReturn(false);
        assertThrows(EntityNotFoundException.class, () -> taskService.delete(1L));
    }

    @Test
    public void testGetAll() {
        when(taskRepository.findAll()).thenReturn(Collections.singletonList(task));
        List<Task> tasks = taskService.getAll();
        assertFalse(tasks.isEmpty());
        assertEquals(1, tasks.size());
        assertEquals(task.getName(), tasks.get(0).getName());
    }

    @Test
    public void testGetAllNoTasks() {
        when(taskRepository.findAll()).thenReturn(Collections.emptyList());
        assertThrows(EntityNotFoundException.class, () -> taskService.getAll());
    }

    @Test
    public void testGetByTodoId() {
        when(taskRepository.findByTodoId(1L)).thenReturn(Collections.singletonList(task));
        List<Task> tasks = taskService.getByTodoId(1L);
        assertFalse(tasks.isEmpty());
        assertEquals(1, tasks.size());
        assertEquals(task.getName(), tasks.get(0).getName());
    }

    @Test
    public void testGetByTodoIdNoTasks() {
        when(taskRepository.findByTodoId(1L)).thenReturn(Collections.emptyList());
        assertThrows(EntityNotFoundException.class, () -> taskService.getByTodoId(1L));
    }
}