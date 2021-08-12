package com.example.BasketService.models;

public enum ProcessType {
    ADD("ADD"),
    REDUCE("REDUCE");

    private String type;

    ProcessType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
