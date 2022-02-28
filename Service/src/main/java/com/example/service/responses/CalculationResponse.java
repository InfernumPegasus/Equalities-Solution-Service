package com.example.service.responses;

import java.util.concurrent.atomic.AtomicLong;

public class CalculationResponse {

    private final AtomicLong Id;
    private final int Root;

    public CalculationResponse(AtomicLong id, int root) {
        this.Id = id;
        this.Root = root;
    }

    public AtomicLong getId()
    { return Id; }

    public int getRoot()
    { return Root; }
}
