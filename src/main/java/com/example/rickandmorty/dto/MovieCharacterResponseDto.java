package com.example.rickandmorty.dto;

import lombok.Data;

@Data
public class MovieCharacterResponseDto {
    private Long id;
    private String name;
    private String status;
    private String gender;
}
