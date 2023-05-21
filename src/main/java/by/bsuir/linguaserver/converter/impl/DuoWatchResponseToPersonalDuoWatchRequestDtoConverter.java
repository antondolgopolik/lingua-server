package by.bsuir.linguaserver.converter.impl;

import by.bsuir.linguaserver.converter.Converter;
import by.bsuir.linguaserver.dto.PersonalDuoWatchRequestDto;
import by.bsuir.linguaserver.entity.DuoWatchResponse;
import by.bsuir.linguaserver.entity.VideoContentLoc;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

@Component
public class DuoWatchResponseToPersonalDuoWatchRequestDtoConverter implements Converter<DuoWatchResponse, PersonalDuoWatchRequestDto> {

    private final VideoContentToCatalogItemDtoConverter videoContentToCatalogItemDtoConverter;

    public DuoWatchResponseToPersonalDuoWatchRequestDtoConverter(VideoContentToCatalogItemDtoConverter videoContentToCatalogItemDtoConverter) {
        this.videoContentToCatalogItemDtoConverter = videoContentToCatalogItemDtoConverter;
    }

    @Override
    public PersonalDuoWatchRequestDto convert(DuoWatchResponse object) {
        var result = new PersonalDuoWatchRequestDto();
        result.setId(object.getDuoWatchRequest().getId());
        result.setVideoContentLocDto(getVideoContentLocDto(object));
        result.setSecondLanguage(object.getDuoWatchRequest().getSecondLanguage());
        result.setStatus(object.getDuoWatchRequest().getStatus());
        result.setRelevanceConfirmationRequired(null);
        // TODO
        result.setPartnerTgUsername("AntonDV");
        return result;
    }

    private PersonalDuoWatchRequestDto.VideoContentLocDto getVideoContentLocDto(DuoWatchResponse object) {
        VideoContentLoc videoContentLoc = object.getDuoWatchRequest().getVideoContentLoc();
        var result = new PersonalDuoWatchRequestDto.VideoContentLocDto();
        result.setId(videoContentLoc.getId());
        result.setLanguage(videoContentLoc.getLanguage());
        result.setCatalogItemDto(videoContentToCatalogItemDtoConverter.convert(videoContentLoc.getVideoContent()));
        return result;
    }
}
