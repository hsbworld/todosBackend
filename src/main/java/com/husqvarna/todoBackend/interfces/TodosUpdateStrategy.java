package com.husqvarna.todoBackend.interfces;

import com.husqvarna.todoBackend.models.Todos;

public interface TodosUpdateStrategy {

    void apply(Todos todo, Object value);

    String getFieldName();

}
