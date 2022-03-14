package com.example.service.controllers;

import com.example.service.process.InputParams;
import com.example.service.process.Solution;
import com.example.service.responses.CalculationResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {

    @GetMapping("/solve")
    public ResponseEntity<CalculationResponse> GetAnswer(
            @RequestParam(value="first_value")   @Nullable Integer first_value,
            @RequestParam(value="second_value")  @Nullable Integer second_value,
            @RequestParam(value="first_border")  @Nullable Integer first_border,
            @RequestParam(value="second_border") @Nullable Integer second_border
            ) {

        var params   = new InputParams(first_value, second_value, first_border, second_border);
        var solution = new Solution(params);

        solution.calculateRoot();
        solution.addToList(params);

        return new ResponseEntity<>(solution.getResponse(), HttpStatus.OK);
    }
}