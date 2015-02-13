package com.epam.theater.controller.exception.message;


public class ExceptionMessage {

    private String error;

    public ExceptionMessage(String message) {
        this.error = message;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

}