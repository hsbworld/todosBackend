package com.husqvarna.todoBackend.interfaces;

import com.husqvarna.todoBackend.models.Todos;

public interface TodosUpdateStrategy {

    void apply(Todos todo, Object value);

    String getFieldName();

}
