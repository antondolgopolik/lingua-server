package by.bsuir.linguaserver.converter.impl;

import by.bsuir.linguaserver.converter.Converter;
import by.bsuir.linguaserver.dto.PersonalDuoWatchRequestDto;
import by.bsuir.linguaserver.dto.PersonalDuoWatchRequestPageDto;
import by.bsuir.linguaserver.entity.DuoWatchRequest;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class DuoWatchRequestPageToPersonalDuoWatchRequestPageDtoConverter implements Converter<Page<DuoWatchRequest>, PersonalDuoWatchRequestPageDto> {

    private final DuoWatchRequestToPersonalDuoWatchRequestDtoConverter duoWatchRequestToPersonalDuoWatchRequestDtoConverter;

    public DuoWatchRequestPageToPersonalDuoWatchRequestPageDtoConverter(DuoWatchRequestToPersonalDuoWatchRequestDtoConverter duoWatchRequestToPersonalDuoWatchRequestDtoConverter) {
        this.duoWatchRequestToPersonalDuoWatchRequestDtoConverter = duoWatchRequestToPersonalDuoWatchRequestDtoConverter;
    }

    @Override
    public PersonalDuoWatchRequestPageDto convert(Page<DuoWatchRequest> object) {
        var result = new PersonalDuoWatchRequestPageDto();
        result.setTotalPages(object.getTotalPages());
        result.setPersonalDuoWatchRequestDtos(getPersonalDuoWatchRequestDtos(object));
        return result;
    }

    private Collection<PersonalDuoWatchRequestDto> getPersonalDuoWatchRequestDtos(Page<DuoWatchRequest> object) {
        return object.getContent().stream()
                .map(duoWatchRequestToPersonalDuoWatchRequestDtoConverter::convert)
                .toList();
    }
}
