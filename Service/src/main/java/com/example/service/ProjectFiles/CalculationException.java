package com.example.service.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
public class CalculationException extends Exception {
    public CalculationException(String message) {
        super(message);
    }
}
