package com.stackroute.transporterservice.exception;

public class DriverNotFound extends RuntimeException{

    String message;

    public DriverNotFound(String message) {
        super();
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public DriverNotFound(){
    }

}
