package com.example.service.controllers;

import com.example.service.process.InputParams;
import com.example.service.services.SolutionService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.Collection;

@RestController
public class SolutionController {

    private SolutionService solutionService;

    @Autowired
    public void setSolutionService(SolutionService solutionService) {
        this.solutionService = solutionService;
    }


    @PostMapping("/url")
    public ResponseEntity<Object> solve(
            @RequestParam(value="first_value")   Integer first_value,
            @RequestParam(value="second_value")  Integer second_value,
            @RequestParam(value="first_border")  Integer first_border,
            @RequestParam(value="second_border") Integer second_border
    ) {
        var params = new InputParams(first_value, second_value, first_border, second_border);
        return new ResponseEntity<>(solutionService.calculate(params), HttpStatus.OK);
    }

    @PostMapping("/json")
    public ResponseEntity<Object> solveSingleJson(
            @RequestBody InputParams param
    ) {
        return new ResponseEntity<>(solutionService.calculate(param), HttpStatus.OK);
    }

    @PostMapping("/bulk")
    public ResponseEntity<Object> solveBulkJson(
            @RequestBody @NotNull Collection<InputParams> inputParamsList
    ) {
        return new ResponseEntity<>(solutionService.calculate(inputParamsList), HttpStatus.OK);
    }

}