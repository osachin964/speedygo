package com.stackroute.customerservice.exception;

public class CustomerAlreadyExistException extends RuntimeException {
    String msg;

    public CustomerAlreadyExistException(String msg) {
        super();
        this.msg = msg;
    }

    public CustomerAlreadyExistException() {
    }

    public String getMsg() {
        return msg;
    }
}
