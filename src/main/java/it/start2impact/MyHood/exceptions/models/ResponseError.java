package it.start2impact.MyHood.exceptions.models;

import java.time.LocalDateTime;

public class ResponseError {
    private String message;
    private LocalDateTime timestamp;
    private String errorType;
    private int errorCode;

    public ResponseError() {
    }

    public ResponseError(String message, LocalDateTime timestamp, String errorType, int errorCode) {
        this.message = message;
        this.timestamp = timestamp;
        this.errorType = errorType;
        this.errorCode = errorCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getErrorType() {
        return errorType;
    }

    public void setErrorType(String errorType) {
        this.errorType = errorType;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

}
