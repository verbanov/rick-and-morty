package com.example.rickandmorty.model.dto;

import lombok.Data;

@Data
public class MovieCharacterResponseDto {
    private Long id;
    private String name;
    private String status;
    private String gender;
}
