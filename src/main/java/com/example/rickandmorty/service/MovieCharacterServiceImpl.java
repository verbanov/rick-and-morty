package com.example.rickandmorty.service;

import com.example.rickandmorty.mapper.MovieCharacterMapper;
import com.example.rickandmorty.model.MovieCharacter;
import com.example.rickandmorty.model.dto.ApiCharacterDto;
import com.example.rickandmorty.model.dto.ApiResponseDto;
import com.example.rickandmorty.repository.MovieCharacterRepository;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class MovieCharacterServiceImpl implements MovieCharacterService {
    private final MovieCharacterRepository repository;
    private final HttpClient httpClient;
    private final MovieCharacterMapper movieCharacterMapper;

    public MovieCharacterServiceImpl(MovieCharacterRepository repository,
                                     HttpClient httpClient,
                                     MovieCharacterMapper movieCharacterMapper) {
        this.repository = repository;
        this.httpClient = httpClient;
        this.movieCharacterMapper = movieCharacterMapper;
    }

    @Override
    public void syncExternalCharacters() {
        String url = "https://rickandmortyapi.com/api/character";
        ApiResponseDto apiResponseDto = httpClient.get(url, ApiResponseDto.class);
        saveDtoToDb(apiResponseDto);
        while (apiResponseDto.getInfo().getNext() != null) {
            apiResponseDto = httpClient.get(apiResponseDto
                    .getInfo()
                    .getNext(),ApiResponseDto.class);
            saveDtoToDb(apiResponseDto);
        }
    }

    private void saveDtoToDb(ApiResponseDto apiResponseDto) {
        Map<Long, ApiCharacterDto> externalDtos = Arrays.stream(apiResponseDto.getResults())
                .collect(Collectors.toMap(ApiCharacterDto::getId, Function.identity()));

        Set<Long> externalIds = externalDtos.keySet();

        List<MovieCharacter> existingCharacters = repository.findAllByExternalIdIn(externalIds);

        Map<Long, MovieCharacter> existingCharactersWithIds = existingCharacters.stream()
                .collect(Collectors.toMap(MovieCharacter::getExternalId, Function.identity()));

        Set<Long> existingIds = existingCharactersWithIds.keySet();

        externalIds.removeAll(existingIds);

        List<MovieCharacter> charactersToSave = externalIds.stream()
                .map(i -> movieCharacterMapper.parseApiCharacterResponseDto(externalDtos.get(i)))
                .collect(Collectors.toList());

        repository.saveAll(charactersToSave);
    }
}
