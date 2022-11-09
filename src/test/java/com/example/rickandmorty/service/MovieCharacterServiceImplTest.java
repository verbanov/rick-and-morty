package com.example.rickandmorty.service;

import com.example.rickandmorty.model.Gender;
import com.example.rickandmorty.model.MovieCharacter;
import com.example.rickandmorty.model.Status;
import com.example.rickandmorty.model.dto.external.ApiCharacterDto;
import com.example.rickandmorty.model.dto.external.ApiResponseDto;
import com.example.rickandmorty.model.dto.mapper.MovieCharacterMapper;
import com.example.rickandmorty.repository.MovieCharacterRepository;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class MovieCharacterServiceImplTest {
    private MovieCharacterMapper movieCharacterMapper = new MovieCharacterMapper();
    @InjectMocks
    private MovieCharacterServiceImpl movieCharacterService;

    @Mock
    private MovieCharacterRepository repository;

    @Test
    void shouldSaveDtoToDb_Ok() {
        ApiCharacterDto summerSmith = new ApiCharacterDto();
        summerSmith.setId(3L);
        summerSmith.setName("Summer Smith");
        summerSmith.setGender("FEMALE");
        summerSmith.setStatus("ALIVE");
        ApiCharacterDto commanderRick = new ApiCharacterDto();
        commanderRick.setId(69L);
        commanderRick.setName("Commander Rick");
        commanderRick.setGender("MALE");
        commanderRick.setStatus("DEAD");
        ApiResponseDto apiResponseDto = new ApiResponseDto();
        apiResponseDto.setInfo(null);
        apiResponseDto.setResults(new ApiCharacterDto[] {summerSmith, commanderRick});

        Set<Long> externalIds = new HashSet<>();
        externalIds.add(summerSmith.getId());
        externalIds.add(commanderRick.getId());

        //Set.of(summerSmith.getId(), commanderRick.getId());

        MovieCharacter summerSmithFromDb = new MovieCharacter();
        //summerSmithFromDb.setId(1L);
        summerSmithFromDb.setExternalId(summerSmith.getId());
        summerSmithFromDb.setName(summerSmith.getName());
        summerSmithFromDb.setGender(Gender.valueOf(summerSmith.getGender()));
        summerSmithFromDb.setStatus(Status.valueOf(summerSmith.getStatus()));

        MovieCharacter commanderRickFromDb = new MovieCharacter();
        //commanderRickFromDb.setId(2L);
        commanderRickFromDb.setExternalId(commanderRick.getId());
        commanderRickFromDb.setName(commanderRick.getName());
        commanderRickFromDb.setGender(Gender.valueOf(commanderRick.getGender()));
        commanderRickFromDb.setStatus(Status.valueOf(commanderRick.getStatus()));

        List<MovieCharacter> charactersToSave = List.of(summerSmithFromDb, commanderRickFromDb);
        List<MovieCharacter> expected = List.of(summerSmithFromDb);

        List<MovieCharacter> empty = new ArrayList<>();
        Mockito.when(repository.findAllByExternalIdIn(externalIds)).thenReturn(empty);
        Mockito.when(repository.saveAll(charactersToSave)).thenReturn(expected);

        List<MovieCharacter> actual = movieCharacterService.saveDtoToDb(apiResponseDto);
    }
}
