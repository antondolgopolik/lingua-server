package by.bsuir.linguaserver.converter.impl;

import by.bsuir.linguaserver.converter.Converter;
import by.bsuir.linguaserver.dto.CatalogItemDto;
import by.bsuir.linguaserver.entity.VideoContent;
import org.springframework.stereotype.Component;

@Component
public class VideoContentToCatalogItemDtoConverter implements Converter<VideoContent, CatalogItemDto> {

    @Override
    public CatalogItemDto convert(VideoContent object) {
        CatalogItemDto catalogItemDto = new CatalogItemDto();
        catalogItemDto.setId(object.getId().toString());
        catalogItemDto.setName(object.getName());
        catalogItemDto.setViews(object.getViews());
        return catalogItemDto;
    }
}
