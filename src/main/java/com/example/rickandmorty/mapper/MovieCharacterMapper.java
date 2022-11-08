package com.example.rickandmorty.mapper;

import com.example.rickandmorty.model.Gender;
import com.example.rickandmorty.model.MovieCharacter;
import com.example.rickandmorty.model.Status;
import com.example.rickandmorty.model.dto.ApiCharacterDto;
import org.springframework.stereotype.Component;

@Component
public class MovieCharacterMapper {
    public MovieCharacter parseApiCharacterResponseDto(ApiCharacterDto characterDto) {
        MovieCharacter movieCharacter = new MovieCharacter();
        movieCharacter.setExternalId(characterDto.getId());
        movieCharacter.setName(characterDto.getName());
        movieCharacter.setGender(Gender.valueOf(characterDto.getGender().toUpperCase()));
        movieCharacter.setStatus(Status.valueOf(characterDto.getStatus().toUpperCase()));
        return movieCharacter;
    }
}
