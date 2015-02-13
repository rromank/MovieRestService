package com.epam.theater.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice(annotations = RestController.class)
public class ExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
        List<ExceptionMessage> exceptionMessages = new ArrayList<ExceptionMessage>();

        for (FieldError fieldError : fieldErrors) {
            exceptionMessages.add(getExceptionMessage(fieldError));
        }

        return new ResponseEntity<Object>(exceptionMessages, headers, status);
    }

    private ExceptionMessage getExceptionMessage(FieldError fieldError) {
        String field = fieldError.getField();
        String rejectedValue = String.valueOf(fieldError.getRejectedValue());
        String code = fieldError.getCode();
        return new ExceptionMessage(field, rejectedValue, code);
    }

}