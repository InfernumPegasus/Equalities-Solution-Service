package com.example.service.input;

import lombok.Data;
import java.util.Objects;

@Data
public class InputParams {
    private Integer firstValue;
    private Integer secondValue;
    private Integer leftBorder;
    private Integer rightBorder;

    public InputParams(
            Integer firstValue,
            Integer secondValue,
            Integer leftBorder,
            Integer rightBorder
    ) throws IllegalArgumentException {
        if (firstValue == null || secondValue == null) {
            throw new IllegalArgumentException("Wrong params provided!");
        }

        this.firstValue  = firstValue;
        this.secondValue = secondValue;

        this.leftBorder  = Objects.requireNonNullElse(leftBorder,  Integer.MIN_VALUE);
        this.rightBorder = Objects.requireNonNullElse(rightBorder, Integer.MAX_VALUE);
    }
}