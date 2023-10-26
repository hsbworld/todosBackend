package com.husqvarna.todoBackend.aspects;

import com.husqvarna.todoBackend.dtos.TodosResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Aspect
@Component
public class ModifyResponseAspect {

    private HttpServletRequest request;

    @Autowired
    ModifyResponseAspect(HttpServletRequest httpServletRequest) {
        this.request = httpServletRequest;
    }

    @Pointcut("execution(* com.husqvarna.todoBackend.controllers.*.getTodoById(**))")
    private void getTodoController(){}

    @Pointcut("execution(* com.husqvarna.todoBackend.controllers.*.updateTodos(..))")
    private void updateTodoController(){}

    @Pointcut("execution(* com.husqvarna.todoBackend.controllers.*.getAllTodos(..))")
    private void getAllTodosController(){}

    @Pointcut("execution(* com.husqvarna.todoBackend.controllers.*.createTodos(..))")
    private void createTodoController(){}

    @AfterReturning( pointcut = "getTodoController() || updateTodoController() || createTodoController()",
            returning = "result" )
    public void addUrlProperty(JoinPoint jp, ResponseEntity<TodosResponse> result) {

        String[] uri = this.request.getRequestURI().split("/");

        if (uri.length <= 2) {
//          Create to do scenario - id is not available in URI, hence need to pass
            result.getBody().setUrl(getUrl(result.getBody().getId()));
        } else {
            result.getBody().setUrl(getUrl());
        }

    }

    @AfterReturning( pointcut = "getAllTodosController()",
            returning = "result")
    public void addUrlPropertyToAllItems(JoinPoint jp, ResponseEntity<List<TodosResponse>> result){

        result.getBody().stream().map(todosResponse -> {
            todosResponse.setUrl(getUrl(todosResponse.getId()));
            return todosResponse;
        } ).collect(Collectors.toList());

    }

    private String getUrl(Long id) {
        return getUrl() + "/" + id;
    }

    private String getUrl() {

        String url = this.request.getScheme() + "://" + this.request.getServerName();

        if (this.request.getServerPort() != 80 && this.request.getServerPort() != 443){
            url = url + ":" + this.request.getServerPort();
        }

        return url + request.getRequestURI();

    }

}