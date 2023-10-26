package com.husqvarna.todoBackend.advices;

import com.husqvarna.todoBackend.exceptions.FailedValidationException;
import com.husqvarna.todoBackend.exceptions.TodoNotFoundException;
import com.husqvarna.todoBackend.responses.GenericErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionsHandler {

    @ExceptionHandler(TodoNotFoundException.class)
    public ResponseEntity<GenericErrorResponse> exceptionsHandler(TodoNotFoundException ex){

//      When record individual record search fails
        GenericErrorResponse genericErrorResponse = new GenericErrorResponse(ex.getHttpStatus(), ex.getExcMessage());

        return ResponseEntity.status(ex.getHttpStatus()).body(genericErrorResponse);

    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<GenericErrorResponse> validationError(MethodArgumentNotValidException ex){

//      This is for validation errors - generated vi hibernate validator

//      Only one validation error is sent back at a time (this is intentional, can have all
//      validations sent back as well, no technical constraints as such)
        return ResponseEntity.status(400).body(new GenericErrorResponse(400,
                ex.getBindingResult().getFieldErrors().get(0).getDefaultMessage()));

    }

    @ExceptionHandler(FailedValidationException.class)
    public ResponseEntity<GenericErrorResponse> failedValidation (FailedValidationException ex) {
        return ResponseEntity.status(ex.getHttpStatus()).body(new GenericErrorResponse(ex.getHttpStatus(),
                ex.getExcMessage()));
    }

}
