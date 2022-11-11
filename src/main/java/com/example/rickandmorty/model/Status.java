package com.example.rickandmorty.model;

public enum Status {
    ALIVE("Alive"),
    DEAD("Dead"),
    UNKNOWN("unknown");

    private String description;

    Status(String description) {
        this.description = description;
    }

}
