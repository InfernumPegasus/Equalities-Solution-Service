package com.example.service.process;

import com.example.service.exceptions.CalculationException;
import com.example.service.responses.CalculationResponse;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

public class Solution {

    private static final Map<InputParams, Integer> solutions = new HashMap<>();

    public void addToList(@NotNull InputParams params) {
        if (!solutions.containsKey(params))
            solutions.put(params, root);
    }

    private static final AtomicLong counter = new AtomicLong();

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
        if (solutions.containsKey(inputParams))
            this.root = solutions.get(inputParams);

        var temp = inputParams.getSecondValue() - inputParams.getFirstValue();

        if (temp < inputParams.getLeftBorder() || temp > inputParams.getRightBorder()) {
            throw new CalculationException("Wrong params!");
        }

        root = temp;
    }

    public CalculationResponse getResponse() {
        System.out.println(solutions);

        counter.incrementAndGet();
        return new CalculationResponse(counter, root);
    }

    public Integer getRoot() {
        return root;
    }

    public void setRoot(@Nullable Integer root) {
        this.root = root;
    }

}