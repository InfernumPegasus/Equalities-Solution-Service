package com.example.service.process;

import org.jetbrains.annotations.NotNull;
import org.springframework.lang.Nullable;

import java.util.Objects;

public class InputParams {

    // переобределение метода equals()
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

    // переопределение метода hashCode()
    @Override
    public int hashCode() {
        return Objects.hash(firstValue, secondValue, leftBorder, rightBorder);
    }

    private @Nullable Integer firstValue;

    private @Nullable Integer secondValue;

    private @Nullable Integer leftBorder;

    private @Nullable Integer rightBorder;

    public InputParams(
            @Nullable Integer firstValue,
            @Nullable Integer secondValue,
            @Nullable Integer leftBorder,
            @Nullable Integer rightBorder
    ) {
        if (firstValue == null || secondValue == null)
            throw new IllegalArgumentException("No arguments!");

        this.firstValue  = firstValue;
        this.secondValue = secondValue;

        this.leftBorder = Objects.requireNonNullElse(leftBorder, Integer.MIN_VALUE);
        this.rightBorder = Objects.requireNonNullElse(rightBorder, Integer.MAX_VALUE);
    }

    @NotNull
    public Integer getFirstValue() {
        assert firstValue != null;
        return firstValue;
    }

    @NotNull
    public Integer getSecondValue() {
        assert secondValue != null;
        return secondValue;
    }

    @NotNull
    public Integer getLeftBorder() {
        assert leftBorder != null;
        return leftBorder;
    }

    @NotNull
    public Integer getRightBorder() {
        assert rightBorder != null;
        return rightBorder;
    }

    public void setFirstValue(@Nullable Integer firstValue) {
        this.firstValue = firstValue;
    }

    public void setSecondValue(@Nullable Integer secondValue) {
        this.secondValue = secondValue;
    }

    public void setLeftBorder(@Nullable Integer leftBorder) {
        this.leftBorder = leftBorder;
    }

    public void setRightBorder(@Nullable Integer rightBorder) {
        this.rightBorder = rightBorder;
    }
}
