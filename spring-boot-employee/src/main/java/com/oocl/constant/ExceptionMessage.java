package com.oocl.constant;

public enum  ExceptionMessage {
    NO_SUCH_DATA("no such data"),

    ILLEGAL_OPERATION("illegal operation");

    private String message;

    ExceptionMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
