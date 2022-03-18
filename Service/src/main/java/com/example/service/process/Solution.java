package com.example.service.process;

import com.example.service.responses.CalculationResponse;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.example.service.cache.SolutionCache.addToMap;
import static com.example.service.cache.SolutionCache.findInCache;

public class Solution {

    Logger logger = LoggerFactory.getLogger(Solution.class);

    private final InputParams inputParams;

    private Integer root;

    public Solution(InputParams params) {
        this.inputParams = params;
    }

    public Solution(
            @Nullable Integer firstValue,
            @Nullable Integer secondValue,
            @Nullable Integer leftBorder,
            @Nullable Integer rightBorder
    ) {
        inputParams = new InputParams(firstValue, secondValue, leftBorder, rightBorder);
    }

    public void calculateRoot() {
        // Trying to find root in cache
        var temp = findInCache(inputParams);
        if (temp != null) {
            logger.info("Root found in cache!");

            root = temp;
            return;
        }

        // If not found
        logger.info("Calculating root...");

        temp = inputParams.getSecondValue() - inputParams.getFirstValue();

        if (temp < inputParams.getLeftBorder() || temp > inputParams.getRightBorder())
            throw new ArithmeticException("Root is not in range!");

        root = temp;

        // Adding { inputParams, root } to cache
        addToMap(inputParams, root);
    }

    public CalculationResponse getResponse() {
        return new CalculationResponse(root);
    }

    public Integer getRoot() {
        return root;
    }

    public void setRoot(@Nullable Integer root) {
        this.root = root;
    }

}