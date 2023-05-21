package by.bsuir.linguaserver.converter.impl;

import by.bsuir.linguaserver.converter.Converter;
import by.bsuir.linguaserver.dto.CatalogItemDto;
import by.bsuir.linguaserver.entity.Genre;
import by.bsuir.linguaserver.entity.VideoContent;
import org.springframework.stereotype.Component;

@Component
public class VideoContentToCatalogItemDtoConverter implements Converter<VideoContent, CatalogItemDto> {

    @Override
    public CatalogItemDto convert(VideoContent object) {
        var result = new CatalogItemDto();
        result.setId(object.getId().toString());
        result.setName(object.getName());
        result.setShortDescription(object.getShortDescription());
        result.setDuration(object.getDuration());
        result.setViews(object.getViews());
        result.setGenres(object.getGenres());
        return result;
    }
}
