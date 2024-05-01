package com.stackroute.transporterservice.exception;

public class TransporterNotFound extends RuntimeException{

    String message;

    public TransporterNotFound(String message) {
        super();
        this.message = message;
    }
    

    @Override
    public String getMessage() {
        return message;
    }

    public TransporterNotFound(){
    }


}
