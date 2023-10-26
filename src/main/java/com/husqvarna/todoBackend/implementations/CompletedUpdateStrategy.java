package com.husqvarna.todoBackend.implementations;

import com.husqvarna.todoBackend.exceptions.FailedValidationException;
import com.husqvarna.todoBackend.interfaces.TodosUpdateStrategy;
import com.husqvarna.todoBackend.models.Todos;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Component;

@Component
public class CompletedUpdateStrategy implements TodosUpdateStrategy {
    @Override
    public void apply(Todos todo, @NotNull Object value) {

        try {
            todo.setCompleted((Boolean) value);
        }
        catch (Exception exception) {
            throw new FailedValidationException(400, "Invalid value provided for 'completed' field");
        }

    }

    @Override
    public String getFieldName() {
        return "completed";
    }
}
