package com.epam.theater.controller.exception.handler;

import com.epam.theater.controller.exception.message.ExceptionMessage;
import com.epam.theater.controller.exception.message.FieldExceptionMessage;
import com.epam.theater.service.exception.ServiceException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice(annotations = RestController.class)
public class RestControllerExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
        List<FieldExceptionMessage> exceptionMessages = new ArrayList<FieldExceptionMessage>();
        for (FieldError fieldError : fieldErrors) {
            exceptionMessages.add(getExceptionMessage(fieldError));
        }
        return new ResponseEntity<Object>(exceptionMessages, headers, status);
    }

    @ExceptionHandler(ServiceException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<Object> handleAllExceptions(ServiceException ex) {
        String message = ex.getMessage();
        return new ResponseEntity<Object>(new ExceptionMessage(message), HttpStatus.OK);
    }

    private FieldExceptionMessage getExceptionMessage(FieldError fieldError) {
        String field = fieldError.getField();
        String rejectedValue = String.valueOf(fieldError.getRejectedValue());
        String code = fieldError.getCode();
        return new FieldExceptionMessage(field, rejectedValue, code);
    }

}