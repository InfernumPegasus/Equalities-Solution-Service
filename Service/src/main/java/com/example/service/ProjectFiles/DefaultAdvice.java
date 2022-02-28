package com.example.service.advice;

import com.example.service.responses.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class DefaultAdvice {


    @ExceptionHandler(Exception.class)
    public ResponseEntity<Response> handleException(Exception e) {
        Response response = new Response(e.getMessage());

        System.out.println(e.getMessage());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
