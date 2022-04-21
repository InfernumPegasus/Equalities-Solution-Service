package com.example.service.responses;

public class Response {
    private final Object result;

    public Response(Object result) {
        this.result = result;
    }

    public Object getResult() {
        return result;
    }
}
