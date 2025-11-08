package it.start2impact.MyHood.exceptions;

import org.springframework.http.HttpStatus;

public class MyHoodException extends Exception {
    private final HttpStatus status;
    public MyHoodException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }
    public HttpStatus getStatus(){return this.status;}
}
