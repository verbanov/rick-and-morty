package com.example.rickandmorty.controller;

import com.example.rickandmorty.service.MovieCharacterService;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
@RequestMapping("/start")
public class DemoController {
    private final MovieCharacterService movieCharacterService;

    public DemoController(MovieCharacterService movieCharacterService) {
        this.movieCharacterService = movieCharacterService;
    }

    @GetMapping
    public String getInfo() {
        movieCharacterService.syncExternalCharacters();
        return "Done";
    }
}
