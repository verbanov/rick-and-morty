package com.example.rickandmorty.service;

import com.example.rickandmorty.model.MovieCharacter;
import com.example.rickandmorty.model.dto.external.ExternalCharacterDto;
import com.example.rickandmorty.model.dto.external.ExternalResponseDto;
import com.example.rickandmorty.model.dto.mapper.MovieCharacterMapper;
import com.example.rickandmorty.repository.MovieCharacterRepository;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class MovieCharacterServiceImpl implements MovieCharacterService {
    @Value("${external_api_url_characters}")
    private String apiUrlCharacters;
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

    public MovieCharacter getRandomCharacter() {
        long countOfCharacters = repository.count();
        long randomId = (long) Math.floor(Math.random() * (countOfCharacters) + 1);
        return repository.findById(randomId).get();
    }

    @Override
    public List<MovieCharacter> findAllByNameContains(String string) {
        return repository.findAllByNameContains(string);
    }

    @Scheduled(cron = "0 */2 * * * *")
    @Override
    public void syncExternalCharacters() {
        ExternalResponseDto externalResponseDto =
                httpClient.get(apiUrlCharacters, ExternalResponseDto.class);
        saveDtoToDb(externalResponseDto);
        while (externalResponseDto.getInfo().getNext() != null) {
            externalResponseDto = httpClient.get(externalResponseDto
                    .getInfo()
                    .getNext(), ExternalResponseDto.class);
            saveDtoToDb(externalResponseDto);
        }
    }

    List<MovieCharacter> saveDtoToDb(ExternalResponseDto externalResponseDto) {
        Map<Long, ExternalCharacterDto> externalDtos =
                Arrays.stream(externalResponseDto.getResults())
                .collect(Collectors.toMap(ExternalCharacterDto::getId, Function.identity()));

        Set<Long> externalIds = externalDtos.keySet();

        List<MovieCharacter> existingCharacters = repository.findAllByExternalIdIn(externalIds);

        Map<Long, MovieCharacter> existingCharactersWithIds = existingCharacters.stream()
                .collect(Collectors.toMap(MovieCharacter::getExternalId, Function.identity()));

        Set<Long> existingIds = existingCharactersWithIds.keySet();

        externalIds.removeAll(existingIds);

        List<MovieCharacter> charactersToSave = externalIds.stream()
                .map(i -> movieCharacterMapper.parseApiCharacterResponseDto(externalDtos.get(i)))
                .collect(Collectors.toList());

        return repository.saveAll(charactersToSave);
    }
}
