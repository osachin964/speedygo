package com.stackroute.transporterservice.exception;

public class DriverAlreadyExist extends RuntimeException{

    String message;

    @Override
    public String getMessage() {
        return message;
    }

    public DriverAlreadyExist(){
    }

    public DriverAlreadyExist(String message) {
        this.message = message;
    }
}
