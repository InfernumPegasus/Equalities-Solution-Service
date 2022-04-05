package com.example.service.advice;

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
    public ResponseEntity<Object> handleException(@NotNull NumberFormatException e) {
        logger.error("Can't convert value to int\n");
        return new ResponseEntity<>("Can't convert value to int\n" + e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {MethodArgumentTypeMismatchException.class})
    public ResponseEntity<Object> handleException(@NotNull MethodArgumentTypeMismatchException e) {
        logger.error("No argument provided in\n" + e.getParameter());
        return new ResponseEntity<>("No argument provided in\n" + e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {ArithmeticException.class})
    public ResponseEntity<Object> handleException(@NotNull ArithmeticException e) {
        logger.error("Error while calculating\n" + e.getMessage());
        return new ResponseEntity<>("Error while calculating\n" + e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {IllegalArgumentException.class})
    public ResponseEntity<Object> handleException(@NotNull IllegalArgumentException e) {
        logger.error("Null argument\n" + e.getMessage());
        return new ResponseEntity<>("Null argument\n" + e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    /*Other Exceptions handler*/

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleException(@NotNull Exception e) {
        logger.error("Unexpected error\n" + e.getMessage());
        return new ResponseEntity<>("Unexpected error\n" + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
