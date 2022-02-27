package com.example.service.controllers;

import com.example.service.exceptions.CalculationException;
import com.example.service.services.MainService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.concurrent.atomic.AtomicLong;

@org.springframework.web.bind.annotation.RestController
public class MainController {

    private final AtomicLong counter = new AtomicLong();

    @GetMapping("/solve")
    public MainService GetAnswer(
            @RequestParam(value="first_value",   defaultValue = "0") int first_value,
            @RequestParam(value="second_value",  defaultValue = "0") int second_value,
            @RequestParam(value="first_border",  defaultValue = "0") int first_border,
            @RequestParam(value="second_border", defaultValue = "0") int second_border
    ) throws CalculationException {

        int root = second_value - first_value;

        if (root < first_border || root > second_border) {
            counter.incrementAndGet();
            throw new CalculationException("Solution not found!");
        }

        return new MainService(counter.incrementAndGet(), root);
    }
}