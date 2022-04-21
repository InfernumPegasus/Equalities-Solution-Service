package com.example.service.process;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import java.util.Objects;

@EqualsAndHashCode
@ToString
@Getter
public class InputParams {

    private final Integer firstValue;

    private final Integer secondValue;

    private final Integer leftBorder;

    private final Integer rightBorder;

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
