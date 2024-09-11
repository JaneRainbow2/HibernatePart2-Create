package com.softserve.itacademy.repository;

import com.softserve.itacademy.model.Task;
import com.softserve.itacademy.model.TaskPriority;
import com.softserve.itacademy.model.ToDo;
import com.softserve.itacademy.model.State;
import com.softserve.itacademy.repository.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.annotation.Rollback;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // Используем реальную БД
public class TaskRepositoryTest {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private ToDoRepository toDoRepository; // Предполагается, что есть репозиторий для ToDo

    @Autowired
    private StateRepository stateRepository; // Предполагается, что есть репозиторий для State

    private ToDo toDo;
    private State state;

    @BeforeEach
    void setup() {
        // Инициализируем ToDo и State
        toDo = new ToDo();
        toDo.setTitle("Test ToDo");
        toDoRepository.save(toDo);

        state = new State();
        state.setName("Test State");
        stateRepository.save(state);

        // Добавляем несколько задач в БД
        Task task1 = new Task();
        task1.setName("Task 1");
        task1.setPriority(TaskPriority.HIGH);
        task1.setTodo(toDo);
        task1.setState(state);
        taskRepository.save(task1);

        Task task2 = new Task();
        task2.setName("Task 2");
        task2.setPriority(TaskPriority.LOW);
        task2.setTodo(toDo);
        task2.setState(state);
        taskRepository.save(task2);
    }

    @Test
    @Rollback(false)
    void testFindByTodoId() {
        // Тестируем метод findByTodoId
        List<Task> tasks = taskRepository.findByTodoId(toDo.getId());

        // Проверяем, что вернулось 2 задачи
        assertFalse(tasks.isEmpty());
        assertEquals(2, tasks.size());

        // Проверяем содержание первой задачи
        assertEquals("Task 1", tasks.get(0).getName());
        assertEquals(TaskPriority.HIGH, tasks.get(0).getPriority());
        assertEquals(toDo, tasks.get(0).getTodo());
    }
}