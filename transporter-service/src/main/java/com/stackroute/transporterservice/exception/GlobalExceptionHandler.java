package com.stackroute.transporterservice.exception;


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
public class GlobalExceptionHandler {

    @ExceptionHandler(TransporterNotFound.class)
    public ResponseEntity<ErrorInfo> transporterNotFound(TransporterNotFound transporterNotFound) {
        ErrorInfo errorInfo = new ErrorInfo();
        errorInfo.setErrorMessage("Transporter Not Found");
        errorInfo.setHttpStatus(HttpStatus.NOT_FOUND.toString());
        errorInfo.setLocalDateTime(LocalDateTime.now());

        return new ResponseEntity<ErrorInfo>(errorInfo, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(TransporterAlreadyExist.class)
    public ResponseEntity<ErrorInfo> transporterAlreadyExist(TransporterAlreadyExist transporterAlreadyExist) {
        ErrorInfo errorInfo = new ErrorInfo();
        errorInfo.setErrorMessage("Transporter Already Exist");
        errorInfo.setHttpStatus(HttpStatus.NOT_FOUND.toString());
        errorInfo.setLocalDateTime(LocalDateTime.now());

        return new ResponseEntity<ErrorInfo>(errorInfo, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DriverNotFound.class)
    public ResponseEntity<ErrorInfo> driverNotFound(DriverNotFound driverNotFound) {
        ErrorInfo errorInfo = new ErrorInfo();
        errorInfo.setErrorMessage("Driver Not Found");
        errorInfo.setHttpStatus(HttpStatus.NOT_FOUND.toString());
        errorInfo.setLocalDateTime(LocalDateTime.now());

        return new ResponseEntity<ErrorInfo>(errorInfo, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DriverAlreadyExist.class)
    public ResponseEntity<ErrorInfo> driverAlreadyExist(DriverAlreadyExist driverAlreadyExist) {
        ErrorInfo errorInfo = new ErrorInfo();
        errorInfo.setErrorMessage("Driver Already Exist or Transporter Already Exist");
        errorInfo.setHttpStatus(HttpStatus.NOT_FOUND.toString());
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
