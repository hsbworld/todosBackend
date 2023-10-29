package com.husqvarna.todoBackend.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.husqvarna.todoBackend.exceptions.FailedValidationException;
import com.husqvarna.todoBackend.models.Todos;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import com.husqvarna.todoBackend.exceptions.TodoNotFoundException;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@TestPropertySource("/application-test.properties")
@AutoConfigureMockMvc
@SpringBootTest
public class TodosControllerIntegrationTest {

    @Autowired
    private JdbcTemplate jdbc;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

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

    @DisplayName("Create todo")
    @Test
    public void createTodo() throws Exception {

        String title = "Todo from JUnit";

        Todos todo = new Todos();
        todo.setTitle(title);

        mockMvc.perform((MockMvcRequestBuilders.post("/todos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(todo))))
                .andExpect(status().is(201))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value(title))
                .andExpect(MockMvcResultMatchers.jsonPath("$.completed").value(false))
                .andExpect(MockMvcResultMatchers.jsonPath("$.order").doesNotExist());

    }

    @DisplayName("Create todo without a title")
    @Test
    public void createTodoWithoutTitle() throws Exception {

        String title = "";

        Todos todo = new Todos();
        todo.setTitle(title);

        mockMvc.perform((MockMvcRequestBuilders.post("/todos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(todo))))
                .andExpect(status().is(400))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.httpStatus").value(400))
                .andExpect(jsonPath("$.excMessage").value("Title should have a value"));

    }

    @DisplayName("Get a valid todo")
    @Test
    public void getTodo () throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/todos/{id}", 101))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

    }

    @DisplayName("Get an invalid todo")
    @Test
    public void getInvalidTodo () throws Exception {

        MvcResult result =  mockMvc.perform(MockMvcRequestBuilders.get("/todos/{id}", 50))
                .andExpect(status().is(404))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        result.getResolvedException();

        Exception resolvedException = result.getResolvedException();
        assertTrue(resolvedException instanceof TodoNotFoundException);

    }

    @DisplayName("Get all todos")
    @Test
    @Sql("/todosForTest.sql")
    public void getTodos () throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/todos"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(5)));

    }

    @DisplayName("Delete todo")
    @Test
    public void deleteTodo() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.delete("/todos/101"))
                .andExpect(status().isOk());

    }

    @DisplayName("Delete invalid todo")
    @Test
    public void deleteInvalidTodo () throws Exception {

        MvcResult result =  mockMvc.perform(MockMvcRequestBuilders.delete("/todos/{id}", 50))
                .andExpect(status().is(404))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        result.getResolvedException();

        Exception resolvedException = result.getResolvedException();
        assertTrue(resolvedException instanceof TodoNotFoundException);

    }

    @DisplayName("Delete all todos")
    @Test
    public void deleteAll() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.delete("/todos"))
                .andExpect(status().isOk());

    }

}

