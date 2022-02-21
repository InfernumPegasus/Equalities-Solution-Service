package com.example.RestService.restservices;

public class MainRestService {

    private final long Id;
    private final int Root;

    public MainRestService(long id, int root) {
        this.Id = id;
        this.Root = root;
    }

    public long getId()
    { return Id; }

    public int getRoot()
    { return Root; }
}