package com.example.rickandmorty.model.dto.external;

import lombok.Data;

@Data
public class ExternalResponseDto {
    private ExternalInfoDto info;
    private ExternalCharacterDto[] results;
}
