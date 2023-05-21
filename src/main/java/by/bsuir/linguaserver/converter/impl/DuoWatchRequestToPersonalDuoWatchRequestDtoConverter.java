package by.bsuir.linguaserver.converter.impl;

import by.bsuir.linguaserver.converter.Converter;
import by.bsuir.linguaserver.dto.PersonalDuoWatchRequestDto;
import by.bsuir.linguaserver.entity.DuoWatchRequest;
import by.bsuir.linguaserver.entity.DuoWatchRequestStatus;
import by.bsuir.linguaserver.entity.VideoContentLoc;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Component
public class DuoWatchRequestToPersonalDuoWatchRequestDtoConverter implements Converter<DuoWatchRequest, PersonalDuoWatchRequestDto> {

    private final VideoContentToCatalogItemDtoConverter videoContentToCatalogItemDtoConverter;

    public DuoWatchRequestToPersonalDuoWatchRequestDtoConverter(VideoContentToCatalogItemDtoConverter videoContentToCatalogItemDtoConverter) {
        this.videoContentToCatalogItemDtoConverter = videoContentToCatalogItemDtoConverter;
    }

    @Override
    public PersonalDuoWatchRequestDto convert(DuoWatchRequest object) {
        var result = new PersonalDuoWatchRequestDto();
        result.setId(object.getId());
        result.setVideoContentLocDto(getVideoContentLocDto(object));
        result.setSecondLanguage(object.getSecondLanguage());
        result.setStatus(object.getStatus());
        result.setRelevanceConfirmationRequired(getRelevanceConfirmationRequired(object));
        // TODO
        result.setPartnerTgUsername("AntonDV");
        return result;
    }

    private PersonalDuoWatchRequestDto.VideoContentLocDto getVideoContentLocDto(DuoWatchRequest object) {
        VideoContentLoc videoContentLoc = object.getVideoContentLoc();
        var result = new PersonalDuoWatchRequestDto.VideoContentLocDto();
        result.setId(videoContentLoc.getId());
        result.setLanguage(videoContentLoc.getLanguage());
        result.setCatalogItemDto(videoContentToCatalogItemDtoConverter.convert(videoContentLoc.getVideoContent()));
        return result;
    }

    private Boolean getRelevanceConfirmationRequired(DuoWatchRequest object) {
        if (object.getStatus() != DuoWatchRequestStatus.OPEN) {
            return null;
        } else {
            return ChronoUnit.DAYS.between(object.getUpdatedAt(), LocalDate.now()) >= 7;
        }
    }
}
