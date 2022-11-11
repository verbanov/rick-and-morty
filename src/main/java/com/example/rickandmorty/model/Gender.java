package com.example.rickandmorty.model;

public enum Gender {
    MALE("Male"),
    FEMALE("Female"),
    GENDERLESS("Genderless"),
    UNKNOWN("unknown");

    private String description;

    Gender(String description) {
        this.description = description;
    }

}
