package com.husqvarna.todoBackend.interfaces;

import com.husqvarna.todoBackend.models.Todos;

import java.util.List;
import java.util.Map;

public interface TodosService {

    public Todos createTodo(Todos todo);

    public Todos getTodoById(Long id);

    public List<Todos> getAllTodos();

    public Todos updateTodos(Long id, Map<String, Object> updates);

    public void deleteTodo(Long id);

    public void deleteTodos();
}
