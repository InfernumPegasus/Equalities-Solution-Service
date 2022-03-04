package com.example.service.process;

import com.example.service.exceptions.CalculationException;
import com.example.service.responses.CalculationResponse;
import org.jetbrains.annotations.NotNull;
import org.springframework.lang.Nullable;

import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;

public class Solution {

    private static final ArrayList<Solution> solutions = new ArrayList<>();

    public static void addToList(@NotNull Solution solution) {
        solutions.add(solution);
    }

    private static final AtomicLong counter = new AtomicLong();

    private @Nullable Integer firstValue;

    private @Nullable Integer secondValue;

    private @Nullable Integer leftBorder;

    private @Nullable Integer rightBorder;

    private Integer root;

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

        checkBorders();

        addToList(this);
    }

    @Nullable public Integer getFirstValue() {
        return firstValue;
    }

    @Nullable public Integer getSecondValue() {
        return secondValue;
    }

    @Nullable public Integer getLeftBorder() {
        return leftBorder;
    }

    @Nullable public Integer getRightBorder() {
        return rightBorder;
    }

    public Integer getRoot() {
        return root;
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

    public void checkBorders() {
        if (leftBorder == null)
            leftBorder = Integer.MIN_VALUE;
        if (rightBorder == null)
            rightBorder = Integer.MAX_VALUE;
    }

    public @Nullable Integer findInCache() {
        for (var item : solutions) {
            if (Objects.equals(item.getFirstValue(), firstValue) &&
                Objects.equals(item.getSecondValue(), secondValue) &&
                Objects.equals(item.getLeftBorder(), leftBorder) &&
                Objects.equals(item.getRightBorder(), rightBorder)
            ) {
                return item.getRoot();
            }
        }
        return null;
    }

    public void calculateRoot()
    {
        if (firstValue == null || secondValue == null) {
            throw new IllegalArgumentException("No arguments!");
        }

        var temp = secondValue - firstValue;

        if (temp < leftBorder || temp > rightBorder) {
            throw new CalculationException("Solution not found!");
        }

        root = temp;
    }

    public CalculationResponse getResponse() {

        // searching root in ArrayList
        root = findInCache();

        // if there is no root then we calculate it
        if (root == null) {
            calculateRoot();
        }

        counter.incrementAndGet();
        return new CalculationResponse(counter, root);
    }
}
