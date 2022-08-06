package com.example.service.controllers;

import com.example.service.input.InputParams;
import com.example.service.services.SolutionService;
import com.example.service.validation.InputParamsValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import javax.validation.Valid;
import java.util.Collection;


/**
 * REST Controller for
 * equalities solving.
 */
@RestController
@RequestMapping("/solve")
public class SolutionController {

    /**
     * Instance of {@link SolutionService}
     * which is used for solving equalities
     */
    private final SolutionService solutionService;

    @Autowired
    public SolutionController(SolutionService solutionService) {
        this.solutionService = solutionService;
    }

    /**
     * Method which is used for solving
     * equality from provided {@link Integer} params.
     *
     * @param firstValue first value in the left part of equality
     * @param secondValue second value in the left part of equality
     * @param leftBorder min border for the root
     * @param rightBorder max border for the root
     * @return {@link ResponseEntity<Integer>} with root and status code CREATED if success
     */
    @PostMapping(value = "/url")
    public ResponseEntity<Integer> solveUrl(
            @RequestParam int firstValue,
            @RequestParam int secondValue,
            @RequestParam int leftBorder,
            @RequestParam int rightBorder
    ) {
        var params = new InputParams(firstValue, secondValue, leftBorder, rightBorder);
        return new ResponseEntity<>(solutionService.solve(params), HttpStatus.CREATED);
    }

    /**
     * Method which is used for solving equality
     * from provided {@link InputParams} from json.
     *
     * @param params input params
     * @return {@link ResponseEntity<Integer>} with root and status code CREATED if success
     */
    @PostMapping("/json")
    public ResponseEntity<Integer> solveSingleJson(
            @RequestBody @Valid InputParams params) {
        return new ResponseEntity<>(solutionService.solve(params), HttpStatus.CREATED);
    }

    /**
     * Method which is used for solving
     * equality from provided
     * {@link Collection} of
     * {@link InputParams} from json.
     *
     * @param inputParamsList collection of input params
     * @return {@link ResponseEntity} with list of roots and status code CREATED
     */
    @PostMapping("/bulk")
    public ResponseEntity<Collection<Integer>> solveBulkJson(
            @RequestBody Collection<InputParams> inputParamsList
    ) {
        var validated =
                InputParamsValidator.getValidByDefault(inputParamsList);
        return new ResponseEntity<>(solutionService.solve(validated), HttpStatus.CREATED);
    }
}