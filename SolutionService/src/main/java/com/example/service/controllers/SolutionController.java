package com.example.service.controllers;

import com.example.service.input.InputParams;
import com.example.service.services.SolutionService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.Collection;

@RestController
@RequestMapping("/solve")
public class SolutionController {

    private SolutionService solutionService;

    @Autowired
    public void setSolutionService(SolutionService solutionService) {
        this.solutionService = solutionService;
    }


    @PostMapping(value = "/url")
    public ResponseEntity<Integer> solveUrl(
            @RequestParam @Nullable Integer firstValue,
            @RequestParam @Nullable Integer secondValue,
            @RequestParam @Nullable Integer leftBorder,
            @RequestParam @Nullable Integer rightBorder) {
        var params = new InputParams(firstValue, secondValue, leftBorder, rightBorder);
        return new ResponseEntity<>(solutionService.calculate(params), HttpStatus.CREATED);
    }

    @PostMapping("/json")
    public ResponseEntity<Integer> solveSingleJson(
            @RequestBody InputParams params) {
        return new ResponseEntity<>(solutionService.calculate(params), HttpStatus.CREATED);
    }

    @PostMapping("/bulk")
    public ResponseEntity<Collection<Integer>> solveBulkJson(
            @RequestBody @NotNull Collection<InputParams> inputParamsList) {
        return new ResponseEntity<>(solutionService.calculate(inputParamsList), HttpStatus.CREATED);
    }

}