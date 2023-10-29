package com.husqvarna.todoBackend.controllers;

import com.husqvarna.todoBackend.exceptions.TodoNotFoundException;
import com.husqvarna.todoBackend.interfaces.TodosService;
import com.husqvarna.todoBackend.models.Todos;
import com.husqvarna.todoBackend.repositories.TodosRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestPropertySource;
import com.husqvarna.todoBackend.exceptions.TodoNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@TestPropertySource("/application-test.properties")
@SpringBootTest
public class TodosServiceUnitTest {

    @MockBean
    private TodosRepository todosRepository;

    @Autowired
    private TodosService todosService;

    @Autowired
    private JdbcTemplate jdbc;

    @Value("${sql.script.insert.todo}")
    private String insertRecord;

    @Value("${sql.script.delete.all}")
    private String deleteAllRecords;

    @BeforeEach
    public void setupDbRecords () {
        jdbc.execute(this.insertRecord);
    }

    @AfterEach
    public void deleteRecords() {
        jdbc.execute(deleteAllRecords);
    }

    @Test
    public void getTodoByIdTest() {

        String title = "Complete Task A";

        Todos todo = new Todos();
        todo.setTitle(title);
        todo.setCompleted(false);
        todo.setOrder(1);

        when(todosRepository.findById(101L)).thenReturn(Optional.of(todo));

        assertEquals("Complete Task A", todosService.getTodoById(101L).getTitle());
        assertEquals(false, todosService.getTodoById(101L).getCompleted());
        assertEquals(1, todosService.getTodoById(101L).getOrder());

    }

    @Test
    public void getTodoByInvalidId(){

        when(todosRepository.findById(50L)).thenThrow(TodoNotFoundException.class);

        assertThrows(TodoNotFoundException.class, () -> todosService.getTodoById(50L));

    }


}
