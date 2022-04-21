package com.example.service.advice;

import com.example.service.responses.Response;
import org.jetbrains.annotations.NotNull;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
public class DefaultAdvice extends ResponseEntityExceptionHandler {

    /*BAD_REQUEST status Exceptions handler*/

    @ExceptionHandler(value = {NumberFormatException.class})
    public ResponseEntity<Response> handleException(@NotNull NumberFormatException e) {
        var msg = "Can't convert value to int\n" + e.getMessage();
        logger.error(msg);
        return new ResponseEntity<>(new Response(msg), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {MethodArgumentTypeMismatchException.class})
    public ResponseEntity<Response> handleException(@NotNull MethodArgumentTypeMismatchException e) {
        var msg = "No argument provided in\n" + e.getParameter();
        logger.error(msg);
        return new ResponseEntity<>(new Response(msg), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {ArithmeticException.class})
    public ResponseEntity<Response> handleException(@NotNull ArithmeticException e) {
        var msg = "Error while calculating\n" + e.getMessage();
        logger.error(msg);
        return new ResponseEntity<>(new Response(msg), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {IllegalArgumentException.class})
    public ResponseEntity<Response> handleException(@NotNull IllegalArgumentException e) {
        var msg = "Null argument\n" + e.getMessage();
        logger.error(msg);
        return new ResponseEntity<>(new Response(msg), HttpStatus.BAD_REQUEST);
    }

    /*Other Exceptions handler*/

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Response> handleException(@NotNull Exception e) {
        var msg = "Unexpected error\n" + e.getMessage();
        logger.error(msg);
        return new ResponseEntity<>(new Response(msg), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
