package com.example.zoo.web.controller;

import com.example.zoo.domain.exception.ExceptionResponse;
import com.example.zoo.domain.exception.ResourceNotFoundException;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ExceptionResponse handleResourceNotFound(ResourceNotFoundException e) {
        return new ExceptionResponse(LocalDateTime.now(), e.getMessage());
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionResponse handleMethodArgumentNotValid(MethodArgumentNotValidException e) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(LocalDateTime.now(),"Validation failed.");
        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
        Map<String, String> errorDetails = fieldErrors.stream()
                .collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage,
                        (existingMessage, newMessage) -> existingMessage + " " + newMessage));
        exceptionResponse.setErrorDetails(errorDetails);
        return exceptionResponse;
    }


    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionResponse handleIllegalStateException(IllegalStateException e) {
        return new ExceptionResponse(LocalDateTime.now(), e.getMessage());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionResponse handleConstraintViolation(ConstraintViolationException e) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(LocalDateTime.now(), "Validation failed(constraint violation).");
        Map<String, String> errorDetails = new HashMap<>();
        String errorMessageDetails = e.getMessage();
        int foundInsertPosition = errorMessageDetails.indexOf("] [insert into ");
        if (foundInsertPosition > 0) {
            errorMessageDetails = errorMessageDetails.substring(0, foundInsertPosition);
        }
        errorDetails.put(e.getConstraintName(), errorMessageDetails);
        exceptionResponse.setErrorDetails(errorDetails);
        return exceptionResponse;
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionResponse handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        return new ExceptionResponse(LocalDateTime.now(), e.getMessage());

    }

}
