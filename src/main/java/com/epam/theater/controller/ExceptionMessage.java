package com.epam.theater.controller;

public class ExceptionMessage {

    private String field;
    private String rejectedValue;
    private String code;

    public ExceptionMessage(String field, String rejectedValue, String code) {
        this.field = field;
        this.rejectedValue = rejectedValue;
        this.code = code;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getRejectedValue() {
        return rejectedValue;
    }

    public void setRejectedValue(String rejectedValue) {
        this.rejectedValue = rejectedValue;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

}