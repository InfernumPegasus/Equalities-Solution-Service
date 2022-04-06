package com.example.service.process;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.jetbrains.annotations.NotNull;
import org.springframework.lang.Nullable;
import java.util.Objects;

@EqualsAndHashCode
@ToString
@Getter
public class InputParams {

    private final @NotNull Integer firstValue;

    private final @NotNull Integer secondValue;

    private final @NotNull Integer leftBorder;

    private final @NotNull Integer rightBorder;

    public InputParams(
            @Nullable Integer firstValue,
            @Nullable Integer secondValue,
            @Nullable Integer leftBorder,
            @Nullable Integer rightBorder
    ) {
        if (firstValue == null && secondValue == null)
            throw new IllegalArgumentException("No first and second values!");

        if (firstValue == null)
            throw new IllegalArgumentException("No first value!");

        if (secondValue == null)
            throw new IllegalArgumentException("No second value!");

        this.firstValue  = firstValue;
        this.secondValue = secondValue;
        this.leftBorder  = Objects.requireNonNullElse(leftBorder,  Integer.MIN_VALUE);
        this.rightBorder = Objects.requireNonNullElse(rightBorder, Integer.MAX_VALUE);
    }
}
