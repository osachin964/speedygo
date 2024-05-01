package com.stackroute.customerservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalException {

    @ExceptionHandler(CustomerNotFoundException.class)
    public ResponseEntity<ErrorInfo> emailIdNotFound(CustomerNotFoundException customerNotFoundException) {
        ErrorInfo errorInfo = new ErrorInfo();
        errorInfo.setErrorMessage("Customer not found");
        errorInfo.setHttpStatus(HttpStatus.NOT_FOUND.toString());
        errorInfo.setLocalDateTime(LocalDateTime.now());

        return new ResponseEntity<ErrorInfo>(errorInfo, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CustomerAlreadyExistException.class)
    public ResponseEntity<ErrorInfo> emailAlreadyExist(CustomerAlreadyExistException customerAlreadyExistException) {
        ErrorInfo errorInfo = new ErrorInfo();
        errorInfo.setErrorMessage(customerAlreadyExistException.getMsg());
        errorInfo.setHttpStatus(HttpStatus.CONFLICT.toString());
        errorInfo.setLocalDateTime(LocalDateTime.now());

        return new ResponseEntity<ErrorInfo>(errorInfo, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleMethodArgsNotValidException(MethodArgumentNotValidException ex) {
        Map<String, String> resp = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String message = error.getDefaultMessage();
            resp.put(fieldName, message);
        });

        return new ResponseEntity<Map<String, String>>(resp, HttpStatus.BAD_REQUEST);
    }


}
