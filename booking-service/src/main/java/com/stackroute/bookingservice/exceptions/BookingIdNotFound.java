package com.stackroute.bookingservice.exceptions;

public class BookingIdNotFound extends RuntimeException {
    String msg;

    public BookingIdNotFound(String msg) {
        super();
        this.msg = msg;
    }

    public BookingIdNotFound() {
    }

    public String getMsg() {
        return msg;
    }
}
