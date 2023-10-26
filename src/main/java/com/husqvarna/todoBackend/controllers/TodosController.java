package com.husqvarna.todoBackend.controllers;

import com.husqvarna.todoBackend.dtos.TodosResponse;
import com.husqvarna.todoBackend.models.Todos;
import com.husqvarna.todoBackend.interfaces.TodosService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/todos")
public class TodosController {

    private final TodosService todosService;

    @Autowired
    TodosController(TodosService todosService){
        this.todosService = todosService;
    }

    @PostMapping
    public ResponseEntity<TodosResponse> createTodos(@Valid  @RequestBody Todos todo) {
        return ResponseEntity.status(201).body(TodosResponse.createResponseObject(this.todosService.createTodo(todo)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TodosResponse> getTodoById (@PathVariable("id") Long id) {
        return ResponseEntity.ok(TodosResponse.createResponseObject(this.todosService.getTodoById(id)));
    }

    @GetMapping
    public ResponseEntity<List<TodosResponse>> getAllTodos (){

        List<Todos> todos = this.todosService.getAllTodos();

        return ResponseEntity.ok( todos.stream().map(TodosResponse::createResponseObject).collect(Collectors.toList()));

    }

    @PatchMapping("/{id}")
    public ResponseEntity<TodosResponse> updateTodos (@PathVariable Long id, @Valid @RequestBody Map<String, Object> updates) {

//      Strategy design pattern is used for update
        return ResponseEntity.ok(TodosResponse.createResponseObject(this.todosService.updateTodos(id, updates)));

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTodo (@PathVariable Long id) {

        this.todosService.deleteTodo(id);
        return ResponseEntity.ok().body("");

    }

    @DeleteMapping
    public ResponseEntity<String> deleteTodos () {

        this.todosService.deleteTodos();
        return ResponseEntity.ok().body("");

    }

}
