package com.husqvarna.todoBackend.dtos;

import com.husqvarna.todoBackend.models.Todos;
import jakarta.validation.constraints.NotBlank;

public class TodosResponse {

    private Long id;

    private String title;

    private Boolean completed;

    private Integer orderNumber;

    private String url;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Boolean getCompleted() {
        return completed;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }

    public Integer getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(Integer orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public TodosResponse(Long id, String title, Boolean completed, Integer orderNumber) {
        this.id = id;
        this.title = title;
        this.completed = completed;
        this.orderNumber = orderNumber;
    }

    public static TodosResponse createResponseObject(Todos todo) {

        return new TodosResponse(todo.getId(),
                                    todo.getTitle(),
                                    todo.getCompleted(),
                                    todo.getOrderNumber() );

    }

    @Override
    public String toString() {
        return "TodosResponse{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", completed=" + completed +
                ", orderNumber=" + orderNumber +
                ", url='" + url + '\'' +
                '}';
    }
}
