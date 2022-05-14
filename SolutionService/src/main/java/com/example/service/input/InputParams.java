package com.example.service.input;

import java.util.Objects;

/**
 * Record which contains input parameters
 * for equalities solving.
 * @param firstValue first value in the left part of equality
 * @param secondValue second value in the left part of equality
 * @param leftBorder min border for the root
 * @param rightBorder max border for the root
 */
public record InputParams(Integer firstValue, Integer secondValue, Integer leftBorder, Integer rightBorder) {
    public InputParams(
            Integer firstValue,
            Integer secondValue,
            Integer leftBorder,
            Integer rightBorder
    ) {
        this.firstValue  = firstValue;
        this.secondValue = secondValue;

        this.leftBorder  = Objects.requireNonNullElse(leftBorder,  Integer.MIN_VALUE);
        this.rightBorder = Objects.requireNonNullElse(rightBorder, Integer.MAX_VALUE);
    }
}