package com.example.service.advice;

import com.example.service.stats.StatsProvider;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import javax.persistence.PersistenceException;

/**
 * This class' aim is exception
 * handling. If exception thrown
 * this class executes handler method
 * and logs info about exception.
 * if exception is not connected with
 * DB, increases amount of wrong
 * requests in Stats.
 */
@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
public class DefaultAdvice extends ResponseEntityExceptionHandler {

    private final StatsProvider statsProvider;

    @Autowired
    public DefaultAdvice(StatsProvider statsProvider) {
        this.statsProvider = statsProvider;
    }

    /* Overridden Spring Exceptions handlers */

    @Override
    protected @NotNull ResponseEntity<Object> handleHttpMessageNotReadable(
            @NotNull HttpMessageNotReadableException ex,
            @NotNull HttpHeaders headers,
            @NotNull HttpStatus status,
            @NotNull WebRequest request) {

        statsProvider.increaseWrongRequests();
        logger.error("HttpMessageNotReadableException: " + ex.getMessage());
        return new ResponseEntity<>(ex.getMessage(), status);
    }

    @Override
    protected @NotNull ResponseEntity<Object> handleNoHandlerFoundException(
            @NotNull NoHandlerFoundException ex,
            @NotNull HttpHeaders headers,
            @NotNull HttpStatus status,
            @NotNull WebRequest request) {
        logger.error("NoHandlerFoundException: " + ex.getMessage(), ex);
        return new ResponseEntity<>("No handler found for: " + ex.getMessage(), headers, status);
    }

    /*BAD_REQUEST status Exceptions handler*/

    @ExceptionHandler(value = {NumberFormatException.class})
    public ResponseEntity<Object> handleNumberFormatException(
            @NotNull NumberFormatException e) {
        logger.error("NumberFormatException: " + e.getMessage());
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {MethodArgumentTypeMismatchException.class})
    public ResponseEntity<Object> handleMethodArgumentTypeMismatchException(
            @NotNull MethodArgumentTypeMismatchException e) {
        logger.error("MethodArgumentTypeMismatchException: " + e.getParameter());
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {ArithmeticException.class})
    public ResponseEntity<Object> handleArithmeticException(
            @NotNull ArithmeticException e) {
        logger.error("ArithmeticException: " + e.getMessage());
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {IllegalArgumentException.class})
    public ResponseEntity<Object> handleIllegalArgumentException(
            @NotNull IllegalArgumentException e) {
        logger.error("IllegalArgumentException: " + e.getMessage());
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    /* SQLException handler */

    @ExceptionHandler(value = {PersistenceException.class})
    public ResponseEntity<Object> handlePersistenceException(
            @NotNull PersistenceException e) {
        logger.error("PersistenceException: " + e.getMessage());
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    /*Other Exceptions handler*/

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleException(@NotNull Exception e) {
        logger.error("Unexpected error: " + e.getMessage());
        return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
