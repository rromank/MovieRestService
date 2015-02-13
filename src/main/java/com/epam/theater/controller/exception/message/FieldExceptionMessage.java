package com.epam.theater.controller.exception.message;

public class FieldExceptionMessage extends ExceptionMessage {

    private String field;
    private String rejectedValue;

    public FieldExceptionMessage(String field, String rejectedValue, String error) {
        super(error);
        this.field = field;
        this.rejectedValue = rejectedValue;
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

}