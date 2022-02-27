package com.example.service.services;

public class MainService {

    private final long Id;
    private final int Root;

    public MainService(long id, int root) {
        this.Id = id;
        this.Root = root;
    }

    public long getId()
    { return Id; }

    public int getRoot()
    { return Root; }
}
