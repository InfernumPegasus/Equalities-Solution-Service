package com.example.service.input;

import lombok.Data;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Data
public class InputParams {

    @NotNull
    private Integer firstValue;

    @NotNull
    private Integer secondValue;

    private Integer leftBorder;
    private Integer rightBorder;

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