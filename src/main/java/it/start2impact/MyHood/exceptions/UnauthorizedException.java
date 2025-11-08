package it.start2impact.MyHood.exceptions;

import org.springframework.http.HttpStatus;

public class UnauthorizedException extends MyHoodException {
    public UnauthorizedException(String message) {
        super(message, HttpStatus.UNAUTHORIZED);
    }
}
