package com.stackroute.emailservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EmailPropertiesEmptyException.class)
    public ResponseEntity<ErrorInfo> emailPropertiesNotFound(EmailPropertiesEmptyException emailPropertiesEmptyException) {
        ErrorInfo errorInfo = new ErrorInfo();
        errorInfo.setErrorMessage(emailPropertiesEmptyException.getMsg());
        errorInfo.setHttpStatus(HttpStatus.BAD_REQUEST.toString());
        errorInfo.setLocalDateTime(LocalDateTime.now());

        return new ResponseEntity<>(errorInfo, HttpStatus.BAD_REQUEST);
    }
}
