package com.example.service.controllers;

import com.example.service.cache.SolutionCache;
import com.example.service.process.InputParams;
import com.example.service.process.Solution;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class MainController {

    private static final Map<InputParams, Integer> solutions = new HashMap<>();

    @GetMapping("/solve")
    public ResponseEntity<Object> solve(
            @RequestParam(value="first_value")   @Nullable Integer first_value,
            @RequestParam(value="second_value")  @Nullable Integer second_value,
            @RequestParam(value="first_border")  @Nullable Integer first_border,
            @RequestParam(value="second_border") @Nullable Integer second_border
            ) {

        var solution = new Solution(new InputParams(first_value, second_value, first_border, second_border));

        solution.calculateRoot();

        return new ResponseEntity<>(solution.getRoot(), HttpStatus.OK);
    }

    @GetMapping("/cache")
    public ResponseEntity<String> printCache() {
        return new ResponseEntity<>(SolutionCache.getStaticStringCache(), HttpStatus.OK);
    }
}