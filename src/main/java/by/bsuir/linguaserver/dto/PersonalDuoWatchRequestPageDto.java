package by.bsuir.linguaserver.dto;

import lombok.Data;

import java.util.Collection;

@Data
public class PersonalDuoWatchRequestPageDto {
    private Integer totalPages;
    private Collection<PersonalDuoWatchRequestDto> personalDuoWatchRequestDtos;
}
