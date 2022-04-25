package com.example.service.process;

import java.util.Objects;

public record InputParams(Integer firstValue, Integer secondValue, Integer leftBorder, Integer rightBorder) {

    public InputParams(
            Integer firstValue,
            Integer secondValue,
            Integer leftBorder,
            Integer rightBorder
    ) {
        this.firstValue  = firstValue;
        this.secondValue = secondValue;

        this.leftBorder = Objects.requireNonNullElse(leftBorder,  Integer.MIN_VALUE);
        this.rightBorder = Objects.requireNonNullElse(rightBorder, Integer.MAX_VALUE);
    }
}
