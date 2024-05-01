package com.stackroute.transporterservice.exception;

public class TransporterAlreadyExist extends RuntimeException{

    String message;

    public TransporterAlreadyExist(String message) {
        super();
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public TransporterAlreadyExist() {
    }

}
