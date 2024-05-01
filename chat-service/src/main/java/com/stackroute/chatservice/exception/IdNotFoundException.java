package com.stackroute.chatservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND,reason = "Id not found")
public class IdNotFoundException extends Throwable{
    private String msg;

    public IdNotFoundException(String msg) {
        super();
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

}
