package com.husqvarna.todoBackend.services;

import com.husqvarna.todoBackend.exceptions.FailedValidationException;
import com.husqvarna.todoBackend.exceptions.TodoNotFoundException;
import com.husqvarna.todoBackend.interfces.TodosUpdateStrategy;
import com.husqvarna.todoBackend.models.Todos;
import com.husqvarna.todoBackend.repositories.TodosRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Valid;
import jakarta.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.*;

@Service
public class TodosService {

    TodosRepository todosRepository;
    Map<String, TodosUpdateStrategy> updateStrategies;

    private Validator validator;

    @Autowired
    TodosService(TodosRepository todosRepository, List<TodosUpdateStrategy> strategies, Validator validator) {

        this.todosRepository = todosRepository;

        this.validator = validator;

        this.updateStrategies = new HashMap<>();

        for (TodosUpdateStrategy strategy : strategies) {
            this.updateStrategies.put(strategy.getFieldName(), strategy);
        }

    }

    @Transactional
    public Todos createTodo(Todos todo) {

        if (todo.getCompleted() == null) {
            todo.setCompleted(false);
        }

        return todosRepository.save(todo);
    }

    public Todos getTodoById(Long id) {

        return this.todosRepository.findById(id)
                .orElseThrow(() -> new TodoNotFoundException(404, "Todo with the ID does not exist"));

    }

    public List<Todos> getAllTodos() {

        return this.todosRepository.findAll();

    }

    @Transactional
    public Todos updateTodos(Long id, Map<String, Object> updates) {

        Todos todo;
        todo = this.todosRepository.findById(id)
                .orElseThrow(() -> new TodoNotFoundException(404, "Todo with the ID does not exist"));

        if (todo != null) {

            updates.forEach((field, value) -> {

                TodosUpdateStrategy strategy = this.updateStrategies.get(field);

                if (strategy != null) {
                    strategy.apply(todo, value);
                }

            });

            Set<ConstraintViolation<Todos>> violations = validator.validate(todo);

            if (!violations.isEmpty()){
                throw new FailedValidationException(400, violations.stream().findFirst().orElse(null).getMessage());
            }

            return todosRepository.save(todo);
        }
        return null;
    }

    @Transactional
    public void deleteTodo (Long id) {

        this.todosRepository.findById(id)
                .orElseThrow(() -> new TodoNotFoundException(404, "Todo with the ID does not exist and hence not deleted"));

        this.todosRepository.deleteById(id);

    }

    @Transactional
    public  void deleteTodos () {
        this.todosRepository.deleteAll();
    }

}
