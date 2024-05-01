package com.stackroute.chatservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.ALREADY_REPORTED,reason = "Id already created")
public class IdAlreadyExistException extends Throwable{
        private String msg;

        public IdAlreadyExistException(String msg) {
            super();
            this.msg = msg;
        }

        public String getMsg() {
            return msg;
        }

    }
