package by.bsuir.linguaserver.converter.impl;

import by.bsuir.linguaserver.converter.Converter;
import by.bsuir.linguaserver.dto.DuoWatchRequestCatalogItemDto;
import by.bsuir.linguaserver.entity.DuoWatchRequest;
import by.bsuir.linguaserver.entity.VideoContentLoc;
import org.springframework.stereotype.Component;

@Component
public class DuoWatchRequestToDuoWatchRequestCatalogItemDtoConverter implements Converter<DuoWatchRequest, DuoWatchRequestCatalogItemDto> {

    private final VideoContentToCatalogItemDtoConverter videoContentToCatalogItemDtoConverter;

    public DuoWatchRequestToDuoWatchRequestCatalogItemDtoConverter(VideoContentToCatalogItemDtoConverter videoContentToCatalogItemDtoConverter) {
        this.videoContentToCatalogItemDtoConverter = videoContentToCatalogItemDtoConverter;
    }

    @Override
    public DuoWatchRequestCatalogItemDto convert(DuoWatchRequest object) {
        var result = new DuoWatchRequestCatalogItemDto();
        result.setId(object.getId());
        result.setVideoContentLocDto(getVideoContentLocDto(object));
        result.setSecondLanguage(object.getSecondLanguage());
        return result;
    }

    private DuoWatchRequestCatalogItemDto.VideoContentLocDto getVideoContentLocDto(DuoWatchRequest object) {
        VideoContentLoc videoContentLoc = object.getVideoContentLoc();
        var result = new DuoWatchRequestCatalogItemDto.VideoContentLocDto();
        result.setId(videoContentLoc.getId());
        result.setLanguage(videoContentLoc.getLanguage());
        result.setCatalogItemDto(videoContentToCatalogItemDtoConverter.convert(videoContentLoc.getVideoContent()));
        return result;
    }
}
