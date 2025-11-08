package it.start2impact.MyHood.exceptions;

import org.springframework.http.HttpStatus;

public class NotFoundException extends MyHoodException {
    public NotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }
}
