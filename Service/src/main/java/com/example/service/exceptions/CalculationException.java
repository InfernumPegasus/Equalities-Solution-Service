package com.example.service.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Wrong params")
public class CalculationException extends RuntimeException {
    public CalculationException(String message) {
        super(message);
    }
}
