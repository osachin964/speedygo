package com.stackroute.registrationservice.exception;

public class IdNotFoundException extends RuntimeException {
    String msg;

    public IdNotFoundException(String msg) {
        super();
        this.msg = msg;
    }

    public IdNotFoundException() {
    }

    public String getMsg() {
        return msg;
    }
}
