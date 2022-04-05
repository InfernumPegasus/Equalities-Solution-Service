package com.example.service.process;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.springframework.lang.Nullable;
import java.util.Objects;

public class InputParams {

    // Method equals() overriding
    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;

        if (obj == null || obj.getClass() != this.getClass())
            return false;

        InputParams params = (InputParams) obj;

        return  Objects.equals(firstValue,  params.firstValue)  &&
                Objects.equals(secondValue, params.secondValue) &&
                Objects.equals(leftBorder,  params.leftBorder)  &&
                Objects.equals(rightBorder, params.rightBorder);
    }

    // Method hashCode() overriding
    @Override
    public int hashCode() {
        return Objects.hash(firstValue, secondValue, leftBorder, rightBorder);
    }

    // Method toString() overriding
    @Override
    public String toString() {
        return "InputParams{" +
                "firstValue=" + firstValue +
                ", secondValue=" + secondValue +
                ", leftBorder=" + leftBorder +
                ", rightBorder=" + rightBorder +
                '}';
    }

    private final @Nullable Integer firstValue;

    private final @Nullable Integer secondValue;

    private final @Nullable Integer leftBorder;

    private final @Nullable Integer rightBorder;

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

    @NotNull
    @Contract(pure = true)
    public Integer getFirstValue() {
        assert firstValue != null;
        return firstValue;
    }

    @NotNull
    @Contract(pure = true)
    public Integer getSecondValue() {
        assert secondValue != null;
        return secondValue;
    }

    @NotNull
    @Contract(pure = true)
    public Integer getLeftBorder() {
        assert leftBorder != null;
        return leftBorder;
    }

    @NotNull
    @Contract(pure = true)
    public Integer getRightBorder() {
        assert rightBorder != null;
        return rightBorder;
    }
}
