package com.example.rickandmorty.model.dto;

import com.example.rickandmorty.model.Gender;
import com.example.rickandmorty.model.Status;
import lombok.Data;

@Data
public class MovieCharacterResponseDto {
    private Long id;
    private String name;
    private Status status;
    private Gender gender;
}
