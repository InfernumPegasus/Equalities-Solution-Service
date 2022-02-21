package com.example.RestService.controllers;

import com.example.RestService.restservices.MainRestService;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@org.springframework.web.bind.annotation.RestController
public class RestController {

    private final AtomicLong counter = new AtomicLong();

    @GetMapping("/solve")
    public MainRestService GetAnswer(
            @RequestParam(value="first_value",   defaultValue = "0") int first_value,
            @RequestParam(value="second_value",  defaultValue = "0") int second_value,
            @RequestParam(value="first_border",  defaultValue = "0") int first_border,
            @RequestParam(value="second_border", defaultValue = "0") int second_border
    ) throws Exception {

        int root = second_value - first_value;

        if (root < first_border || root > second_border)
            throw new Exception("BAD!!!");

        return new MainRestService(counter.incrementAndGet(), root);
    }
}