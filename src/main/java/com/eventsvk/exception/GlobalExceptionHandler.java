package com.eventsvk.exception;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, Object> handleConstraintViolation(ConstraintViolationException ex) {
        Map<String, Object> response = new HashMap<>();
        response.put("error", "Validation failed");
        response.put("status", HttpStatus.BAD_REQUEST.value());

        List<String> errors = ex.getConstraintViolations().stream()
                .map(violation -> {
                    String paramName = violation.getPropertyPath().toString();
                    String message = violation.getMessage();
                    return paramName + ": " + message;
                })
                .toList();

        response.put("errors", errors);
        return response;
    }
}
