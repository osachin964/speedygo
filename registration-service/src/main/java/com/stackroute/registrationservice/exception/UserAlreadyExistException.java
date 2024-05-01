package com.stackroute.registrationservice.exception;

public class UserAlreadyExistException extends RuntimeException {
    String msg;

    public UserAlreadyExistException(String msg) {
        super();
        this.msg = msg;
    }

    public UserAlreadyExistException() {
    }

    public String getMsg() {
        return msg;
    }
}
