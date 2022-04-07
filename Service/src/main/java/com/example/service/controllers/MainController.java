package com.example.service.controllers;

import com.example.service.SpringConfig;
import com.example.service.cache.Cache;
import com.example.service.process.InputParams;
import com.example.service.process.Solution;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.service.process.Solution.counter;

@RestController
public class MainController {

    public static AnnotationConfigApplicationContext context =
            new AnnotationConfigApplicationContext(SpringConfig.class);

    @GetMapping("/solve")
    public ResponseEntity<Object> solve(
            @RequestParam(value="first_value")   @Nullable Integer first_value,
            @RequestParam(value="second_value")  @Nullable Integer second_value,
            @RequestParam(value="first_border")  @Nullable Integer first_border,
            @RequestParam(value="second_border") @Nullable Integer second_border
            ) {

        var params = new InputParams(first_value, second_value, first_border, second_border);
        Solution.calculateRoot(params);

        return new ResponseEntity<>(Solution.getRoot(), HttpStatus.OK);
    }

    @PostMapping("/solve_json")
    public ResponseEntity<Object> solveSingleJson(
            @RequestBody @NotNull InputParams param
    ) {
        Solution.calculateRoot(param);

        return new ResponseEntity<>(Solution.getRoot(), HttpStatus.OK);
    }

    @PostMapping("/solve_bulk")
    public ResponseEntity<Object> solveBulkJson(
            @RequestBody @NotNull List<InputParams> params
    ) {

        var roots = params
                .stream()
                .peek(Solution::calculateRoot)
                .map(e -> Solution.getRoot())
                .collect(Collectors.toList());

        return new ResponseEntity<>(roots, HttpStatus.OK);
    }

    @GetMapping("/cache")
    public ResponseEntity<String> printCache() {
        return new ResponseEntity<>(Cache.getStringCache(), HttpStatus.OK);
    }

    @GetMapping("/counter")
    public ResponseEntity<String> printCounter() {
        var response = "Requests count: " + counter.getCount();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}