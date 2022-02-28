package com.example.service.process;

import com.example.service.exceptions.CalculationException;
import com.example.service.responses.CalculationResponse;
import org.springframework.lang.Nullable;

import java.util.concurrent.atomic.AtomicLong;

public class Solution {

    private static final AtomicLong counter = new AtomicLong();

    private @Nullable
    Integer firstValue;

    private @Nullable
    Integer secondValue;

    private @Nullable
    Integer leftBorder;

    private @Nullable
    Integer rightBorder;

    private @Nullable
    Integer root;



    @Nullable
    public Integer getFirstValue() {
        return firstValue;
    }

    @Nullable
    public Integer getSecondValue() {
        return secondValue;
    }

    @Nullable
    public Integer getLeftBorder() {
        return leftBorder;
    }

    @Nullable
    public Integer getRightBorder() {
        return rightBorder;
    }

    public void setLeftBorder(@Nullable Integer leftBorder) {
        this.leftBorder = leftBorder;
    }

    public void setRightBorder(@Nullable Integer rightBorder) {
        this.rightBorder = rightBorder;
    }

    public void setRoot(@Nullable Integer root) {
        this.root = root;
    }

    public void setFirstValue(@Nullable Integer firstValue) {
        this.firstValue = firstValue;
    }

    public void setSecondValue(@Nullable Integer secondValue) {
        this.secondValue = secondValue;
    }

    public Solution(
            @Nullable Integer firstValue,
            @Nullable Integer secondValue,
            @Nullable Integer leftBorder,
            @Nullable Integer rightBorder
    ) {
        this.firstValue = firstValue;
        this.secondValue = secondValue;
        this.leftBorder = leftBorder;
        this.rightBorder = rightBorder;
        this.root = null;
    }

    public CalculationResponse getResponse() throws CalculationException {
        System.out.println("SOLUTION CREATED!");
        if (firstValue == null || secondValue == null) {
            throw new IllegalArgumentException("No arguments!");
        }

        if (leftBorder == null || rightBorder == null) {
            leftBorder = Integer.MIN_VALUE;
            rightBorder = Integer.MAX_VALUE;
        }

        root = secondValue - firstValue;

        if (root < leftBorder || root > rightBorder) {
            counter.incrementAndGet();
            throw new CalculationException("Solution not found!");
        }

        counter.incrementAndGet();
        return new CalculationResponse(counter, root);
    }

    public Solution() {
        secondValue = firstValue = rightBorder = leftBorder = null;
    }

}
