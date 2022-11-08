package com.example.rickandmorty.model.dto;

import lombok.Data;

@Data
public class ApiInfoDto {
    private Long count;
    private Long pages;
    private String next;
    private String prev;
}
