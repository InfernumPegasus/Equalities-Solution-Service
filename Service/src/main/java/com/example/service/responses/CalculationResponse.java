package com.example.service.responses;

public class CalculationResponse {

    private final int Root;

    public CalculationResponse(int root) {
        this.Root = root;
    }

    public int getRoot()
    { return Root; }
}
