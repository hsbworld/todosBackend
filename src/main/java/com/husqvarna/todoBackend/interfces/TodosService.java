package com.husqvarna.todoBackend.interfces;

import com.husqvarna.todoBackend.exceptions.TodoNotFoundException;
import com.husqvarna.todoBackend.models.Todos;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolation;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface TodosService {

    public Todos createTodo(Todos todo);

    public Todos getTodoById(Long id);

    public List<Todos> getAllTodos();

    public Todos updateTodos(Long id, Map<String, Object> updates);

    public void deleteTodo(Long id);

    public void deleteTodos();
}
