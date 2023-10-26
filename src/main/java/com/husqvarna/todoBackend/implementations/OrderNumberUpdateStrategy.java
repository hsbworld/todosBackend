package com.husqvarna.todoBackend.implementations;

import com.husqvarna.todoBackend.exceptions.FailedValidationException;
import com.husqvarna.todoBackend.interfces.TodosUpdateStrategy;
import com.husqvarna.todoBackend.models.Todos;
import org.springframework.stereotype.Component;

@Component
public class OrderNumberUpdateStrategy implements TodosUpdateStrategy {
    @Override
    public void apply(Todos todo, Object value) {

        try {
            todo.setOrder((Integer) value);;
        }
        catch (Exception exception) {
            throw new FailedValidationException(400, "Invalid value provided for 'orderNumber' field");
        }

    }

    @Override
    public String getFieldName() {
        return "order";
    }
}
