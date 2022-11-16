package com.example.rickandmorty.service;

import com.example.rickandmorty.model.Gender;
import com.example.rickandmorty.model.MovieCharacter;
import com.example.rickandmorty.model.Status;
import com.example.rickandmorty.model.dto.external.ExternalCharacterDto;
import com.example.rickandmorty.model.dto.external.ExternalResponseDto;
import com.example.rickandmorty.model.dto.mapper.MovieCharacterMapper;
import com.example.rickandmorty.repository.MovieCharacterRepository;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class MovieCharacterServiceImplTest {
    @InjectMocks
    private MovieCharacterServiceImpl movieCharacterService;

    @Mock
    private MovieCharacterRepository repository;

    @Mock
    private MovieCharacterMapper movieCharacterMapper;

    @Test
    void shouldSaveDtoToDb_Ok() {
        ExternalCharacterDto summerSmith = new ExternalCharacterDto();
        summerSmith.setId(3L);
        summerSmith.setName("Summer Smith");
        summerSmith.setGender("FEMALE");
        summerSmith.setStatus("ALIVE");
        ExternalCharacterDto commanderRick = new ExternalCharacterDto();
        commanderRick.setId(69L);
        commanderRick.setName("Commander Rick");
        commanderRick.setGender("MALE");
        commanderRick.setStatus("DEAD");

        ExternalResponseDto externalResponseDto = new ExternalResponseDto();
        externalResponseDto.setInfo(null);
        externalResponseDto.setResults(new ExternalCharacterDto[] {summerSmith, commanderRick});

        Set<Long> externalIds = new HashSet<>();
        externalIds.add(summerSmith.getId());
        externalIds.add(commanderRick.getId());

        MovieCharacter summerSmithFromDb = new MovieCharacter();
        summerSmithFromDb.setExternalId(summerSmith.getId());
        summerSmithFromDb.setName(summerSmith.getName());
        summerSmithFromDb.setGender(Gender.valueOf(summerSmith.getGender()));
        summerSmithFromDb.setStatus(Status.valueOf(summerSmith.getStatus()));
        MovieCharacter commanderRickFromDb = new MovieCharacter();
        commanderRickFromDb.setExternalId(commanderRick.getId());
        commanderRickFromDb.setName(commanderRick.getName());
        commanderRickFromDb.setGender(Gender.valueOf(commanderRick.getGender()));
        commanderRickFromDb.setStatus(Status.valueOf(commanderRick.getStatus()));

        List<MovieCharacter> charactersToSave = List.of(summerSmithFromDb, commanderRickFromDb);
        List<MovieCharacter> expected = List.of(summerSmithFromDb);

        Mockito.when(repository.findAllByExternalIdIn(externalIds)).thenReturn(new ArrayList<>());
        Mockito.when(repository.saveAll(charactersToSave)).thenReturn(expected);
        Mockito.when(movieCharacterMapper.parseApiCharacterResponseDto(summerSmith)).thenReturn(summerSmithFromDb);
        Mockito.when(movieCharacterMapper.parseApiCharacterResponseDto(commanderRick)).thenReturn(commanderRickFromDb);

        List<MovieCharacter> actual = movieCharacterService.saveDtoToDb(externalResponseDto);

        Assertions.assertNotNull(actual);
        Assertions.assertEquals(expected, actual);
    }
}
