package com.stackroute.customerservice.exception;

public class CustomerNotFoundException extends RuntimeException {
    String msg;

    public CustomerNotFoundException(String msg) {
        super();
        this.msg = msg;
    }

    public CustomerNotFoundException() {
    }

    public String getMsg() {
        return msg;
    }
}
