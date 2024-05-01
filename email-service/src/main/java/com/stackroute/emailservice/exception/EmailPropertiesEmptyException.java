package com.stackroute.emailservice.exception;

public class EmailPropertiesEmptyException extends RuntimeException{

    String msg;

    public EmailPropertiesEmptyException(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
