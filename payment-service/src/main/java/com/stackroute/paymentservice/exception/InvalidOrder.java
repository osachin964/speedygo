package com.stackroute.paymentservice.exception;

public class InvalidOrder extends RuntimeException{

    public InvalidOrder(){

    }
    public InvalidOrder(String message){
        super(message);
    }
}
