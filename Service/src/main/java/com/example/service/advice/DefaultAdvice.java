package com.example.service.advice;

import com.example.service.responses.ExceptionResponse;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class DefaultAdvice extends ResponseEntityExceptionHandler {

    /*BAD_REQUEST status Exceptions handler*/

    @ExceptionHandler(value = {NumberFormatException.class})
    public ResponseEntity<ExceptionResponse> handleException(@NotNull NumberFormatException e) {
        logger.error("Can't convert value to int", e);
        return new ResponseEntity<>(new ExceptionResponse("Can't convert value to int"), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {MethodArgumentTypeMismatchException.class})
    public ResponseEntity<ExceptionResponse> handleException(@NotNull MethodArgumentTypeMismatchException e) {
        logger.error("No argument provided", e);
        return new ResponseEntity<>(new ExceptionResponse("Wrong input"), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {ArithmeticException.class})
    public ResponseEntity<ExceptionResponse> handleException(@NotNull ArithmeticException e) {
        logger.error("Error while calculating:", e);
        return new ResponseEntity<>(new ExceptionResponse(e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    /*INTERNAL_SERVER_ERROR status Exceptions handler*/

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponse> handleException(@NotNull Exception e) {
        logger.error("Unexpected error", e);
        return new ResponseEntity<>(new ExceptionResponse(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
