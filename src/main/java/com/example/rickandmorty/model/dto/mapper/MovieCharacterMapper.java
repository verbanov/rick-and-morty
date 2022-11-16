package com.example.rickandmorty.model.dto.mapper;

import com.example.rickandmorty.model.Gender;
import com.example.rickandmorty.model.MovieCharacter;
import com.example.rickandmorty.model.Status;
import com.example.rickandmorty.model.dto.MovieCharacterResponseDto;
import com.example.rickandmorty.model.dto.external.ExternalCharacterDto;
import org.springframework.stereotype.Component;

@Component
public class MovieCharacterMapper {
    public MovieCharacter parseApiCharacterResponseDto(ExternalCharacterDto characterDto) {
        MovieCharacter movieCharacter = new MovieCharacter();
        movieCharacter.setExternalId(characterDto.getId());
        movieCharacter.setName(characterDto.getName());
        movieCharacter.setGender(Gender.valueOf(characterDto.getGender().toUpperCase()));
        movieCharacter.setStatus(Status.valueOf(characterDto.getStatus().toUpperCase()));
        return movieCharacter;
    }

    public MovieCharacterResponseDto modelToResponseDto(MovieCharacter movieCharacter) {
        MovieCharacterResponseDto movieCharacterResponseDto = new MovieCharacterResponseDto();
        movieCharacterResponseDto.setId(movieCharacter.getExternalId());
        movieCharacterResponseDto.setName(movieCharacter.getName());
        movieCharacterResponseDto.setGender(movieCharacter.getGender());
        movieCharacterResponseDto.setStatus(movieCharacter.getStatus());
        return movieCharacterResponseDto;
    }
}
