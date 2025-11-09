package it.start2impact.MyHood.exceptions.handler;

import it.start2impact.MyHood.exceptions.MyHoodException;
import it.start2impact.MyHood.exceptions.models.ResponseError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(MyHoodException.class)
    public ResponseEntity<ResponseError> handleMyHoodException(MyHoodException e){
        logger.error("GlobalExceptionHandler.handleMyHoodException", e);
        ResponseError error = new ResponseError();
        error.setErrorCode(e.getStatus().value());
        error.setErrorType(e.getStatus().getReasonPhrase());
        error.setMessage(e.getMessage());
        error.setTimestamp(LocalDateTime.now());

        return ResponseEntity.status(e.getStatus()).body(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseError> handleMethodArgumentNotValidException(MethodArgumentNotValidException e){
        logger.error("GlobalExceptionHandler.handleMethodArgumentNotValidException", e);
        ResponseError error = new ResponseError();
        HttpStatus status = (HttpStatus) e.getStatusCode();
        error.setErrorCode(status.value());
        error.setErrorType(status.getReasonPhrase());
        List<String> validationErrors = e.getBindingResult().getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .toList();
        error.setMessage(String.join("; ", validationErrors));
        error.setTimestamp(LocalDateTime.now());

        return ResponseEntity.status(status).body(error);

    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseError> handleException(Exception e){
        logger.error("GlobalExceptionHandler.handleException", e);
        ResponseError error = new ResponseError();
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        error.setErrorCode(status.value());
        error.setErrorType(status.getReasonPhrase());
        error.setMessage("Service not available");
        error.setTimestamp(LocalDateTime.now());

        return ResponseEntity.status(status).body(error);
    }



}


