package com.example.rickandmorty.model.dto.external;

import lombok.Data;

@Data
public class ApiInfoDto {
    private Long count;
    private Long pages;
    private String next;
    private String prev;
}