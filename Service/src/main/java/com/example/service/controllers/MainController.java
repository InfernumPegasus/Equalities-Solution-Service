package com.example.service.controllers;

import com.example.service.SpringConfig;
import com.example.service.responses.Response;
import com.example.service.services.CacheService;
import com.example.service.stats.Stats;
import com.example.service.stats.StatsProvider;
import com.example.service.process.InputParams;
import com.example.service.services.SolutionService;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
public class MainController {

    public static AnnotationConfigApplicationContext context =
            new AnnotationConfigApplicationContext(SpringConfig.class);

    private static final SolutionService solution =
            context.getBean("solutionService", SolutionService.class);

    private static final Stats stats =
            context.getBean("stats", Stats.class);

    @GetMapping("/solve")
    public ResponseEntity<Object> solve(
            @RequestParam(value="first_value")   Integer first_value,
            @RequestParam(value="second_value")  Integer second_value,
            @RequestParam(value="first_border")  Integer first_border,
            @RequestParam(value="second_border") Integer second_border
            ) {

        var params = new InputParams(first_value, second_value, first_border, second_border);
        return getObjectResponseEntity(params);
    }

    @PostMapping("/solve_json")
    public ResponseEntity<Object> solveSingleJson(
            @RequestBody InputParams param
    ) {
        return getObjectResponseEntity(param);
    }

    @PostMapping("/solve_bulk")
    public ResponseEntity<Object> solveBulkJson(
            @RequestBody @NotNull List<InputParams> params
    ) {

        var roots = params
                .stream()
                .filter(SolutionService::isCorrectParams)
                .map(solution::calculateRoot)
                .filter(Objects::nonNull)
                .peek(StatsProvider::addRoot)
                .collect(Collectors.toList());

        return new ResponseEntity<>(roots, HttpStatus.OK);
    }

    @GetMapping("/cache")
    public ResponseEntity<String> printCache() {
        return new ResponseEntity<>(CacheService.getStringCache(), HttpStatus.OK);
    }

    @GetMapping("/stats")
    public ResponseEntity<Object> printStats() {
        StatsProvider.calculate(stats);
        return new ResponseEntity<>(StatsProvider.calculateAndGet(stats), HttpStatus.OK);
    }

    @NotNull
    private ResponseEntity<Object> getObjectResponseEntity(@RequestBody InputParams param) {
        if (!SolutionService.isCorrectParams(param)) {
            throw new RuntimeException("Wrong params!");
        }

        var root = solution.calculateRoot(param);
        if (root == null) {
            throw new ArithmeticException("Root is not in range!");
        }
        StatsProvider.addRoot(root);

        return new ResponseEntity<>(new Response(root), HttpStatus.OK);
    }
}