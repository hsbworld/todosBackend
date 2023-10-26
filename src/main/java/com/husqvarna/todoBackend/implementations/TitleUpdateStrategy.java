package com.husqvarna.todoBackend.implementations;

import com.husqvarna.todoBackend.exceptions.FailedValidationException;
import com.husqvarna.todoBackend.interfaces.TodosUpdateStrategy;
import com.husqvarna.todoBackend.models.Todos;
import org.springframework.stereotype.Component;

@Component
public class TitleUpdateStrategy implements TodosUpdateStrategy {
    @Override
    public void apply(Todos todo, Object value) {

        try {
            todo.setTitle((String) value);
        }
        catch (Exception exception) {
            throw new FailedValidationException(400, "Invalid value provided for 'title' field");
        }

    }

    @Override
    public String getFieldName() {
        return "title";
    }

}
