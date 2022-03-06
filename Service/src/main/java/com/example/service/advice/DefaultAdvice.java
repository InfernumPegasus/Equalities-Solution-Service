package com.example.service.advice;

import com.example.service.exceptions.CalculationException;
import com.example.service.responses.ExceptionResponse;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class DefaultAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(CalculationException.class)
    public ResponseEntity<ExceptionResponse> handleException(@NotNull CalculationException e) {
        logger.error("ERROR CODE 400", e);
        return new ResponseEntity<>(new ExceptionResponse(e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponse> handleException(@NotNull Exception e) {
        logger.error("ERROR CODE 500", e);
        return new ResponseEntity<>(new ExceptionResponse(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
