package com.example.rickandmorty.controller;

import com.example.rickandmorty.model.dto.MovieCharacterResponseDto;
import com.example.rickandmorty.model.dto.mapper.MovieCharacterMapper;
import com.example.rickandmorty.service.MovieCharacterService;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("characters")
public class MovieCharacterController {
    private final MovieCharacterService movieCharacterService;
    private final MovieCharacterMapper movieCharacterMapper;

    public MovieCharacterController(MovieCharacterService movieCharacterService,
                                    MovieCharacterMapper movieCharacterMapper) {
        this.movieCharacterService = movieCharacterService;
        this.movieCharacterMapper = movieCharacterMapper;
    }

    @GetMapping("/random")
    public MovieCharacterResponseDto getRandomCharacter() {
        return movieCharacterMapper.modelToResponseDto(movieCharacterService.getRandomCharacter());
    }

    @GetMapping("/by-name")
    public List<MovieCharacterResponseDto> getCharactersContainsString(
            @RequestParam(name = "name") String namePart) {
        return movieCharacterService.findAllByNameContains(namePart)
                .stream()
                .map(movieCharacterMapper::modelToResponseDto)
                .collect(Collectors.toList());
    }
}
