package com.husqvarna.todoBackend.advices;

import com.husqvarna.todoBackend.exceptions.FailedValidationException;
import com.husqvarna.todoBackend.exceptions.TodoNotFoundException;
import com.husqvarna.todoBackend.responses.GenericErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;

@ControllerAdvice
public class ExceptionsHandler {

    @ExceptionHandler(TodoNotFoundException.class)
    public ResponseEntity<GenericErrorResponse> exceptionsHandler(TodoNotFoundException ex){

        GenericErrorResponse genericErrorResponse = new GenericErrorResponse(ex.getHttpStatus(), ex.getExcMessage());

        return ResponseEntity.status(ex.getHttpStatus()).body(genericErrorResponse);

    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<GenericErrorResponse> validationError(MethodArgumentNotValidException ex){
//        List<ObjectError> errors = ex.getAllErrors();
//        List<FieldError> fieldErrors = ex.getFieldErrors();

        return ResponseEntity.status(400).body(new GenericErrorResponse(400,
                ex.getBindingResult().getFieldErrors().get(0).getDefaultMessage()));

    }

    @ExceptionHandler(FailedValidationException.class)
    public ResponseEntity<GenericErrorResponse> failedvalidation (FailedValidationException ex) {
        return ResponseEntity.status(ex.getHttpStatus()).body(new GenericErrorResponse(ex.getHttpStatus(),
                ex.getExcMessage()));
    }

}
