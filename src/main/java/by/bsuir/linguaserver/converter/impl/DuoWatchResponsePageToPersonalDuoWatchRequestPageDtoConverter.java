package by.bsuir.linguaserver.converter.impl;

import by.bsuir.linguaserver.converter.Converter;
import by.bsuir.linguaserver.dto.PersonalDuoWatchRequestDto;
import by.bsuir.linguaserver.dto.PersonalDuoWatchRequestPageDto;
import by.bsuir.linguaserver.entity.DuoWatchResponse;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class DuoWatchResponsePageToPersonalDuoWatchRequestPageDtoConverter implements Converter<Page<DuoWatchResponse>, PersonalDuoWatchRequestPageDto> {

    private final DuoWatchResponseToPersonalDuoWatchRequestDtoConverter duoWatchResponseToPersonalDuoWatchRequestDtoConverter;

    public DuoWatchResponsePageToPersonalDuoWatchRequestPageDtoConverter(DuoWatchResponseToPersonalDuoWatchRequestDtoConverter duoWatchResponseToPersonalDuoWatchRequestDtoConverter) {
        this.duoWatchResponseToPersonalDuoWatchRequestDtoConverter = duoWatchResponseToPersonalDuoWatchRequestDtoConverter;
    }

    @Override
    public PersonalDuoWatchRequestPageDto convert(Page<DuoWatchResponse> object) {
        var result = new PersonalDuoWatchRequestPageDto();
        result.setTotalPages(object.getTotalPages());
        result.setPersonalDuoWatchRequestDtos(getPersonalDuoWatchRequestDtos(object));
        return result;
    }

    private Collection<PersonalDuoWatchRequestDto> getPersonalDuoWatchRequestDtos(Page<DuoWatchResponse> object) {
        return object.getContent().stream()
                .map(duoWatchResponseToPersonalDuoWatchRequestDtoConverter::convert)
                .toList();
    }
}
