package com.example.service.process;

import com.example.service.cache.SolutionCache;
import org.jetbrains.annotations.Nullable;

public class Solution {

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
        var temp = SolutionCache.find(inputParams);
        if (temp != null) {
            setRoot(temp);

            return;
        }

        // If not found
        temp = inputParams.getSecondValue() - inputParams.getFirstValue();

        if (temp < inputParams.getLeftBorder() || temp > inputParams.getRightBorder())
            throw new ArithmeticException("Root is not in range!");

        setRoot(temp);

        // Adding { inputParams, root } to cache
        SolutionCache.add(inputParams, root);
    }

    public Integer getRoot() {
        return root;
    }

    public void setRoot(@Nullable Integer root) {
        this.root = root;
    }

}