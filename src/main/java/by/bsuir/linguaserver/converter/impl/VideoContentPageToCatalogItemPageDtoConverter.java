package by.bsuir.linguaserver.converter.impl;

import by.bsuir.linguaserver.converter.Converter;
import by.bsuir.linguaserver.dto.CatalogItemDto;
import by.bsuir.linguaserver.dto.CatalogItemPageDto;
import by.bsuir.linguaserver.entity.VideoContent;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class VideoContentPageToCatalogItemPageDtoConverter implements Converter<Page<VideoContent>, CatalogItemPageDto> {

    private final VideoContentToCatalogItemDtoConverter videoContentToCatalogItemDtoConverter;

    public VideoContentPageToCatalogItemPageDtoConverter(VideoContentToCatalogItemDtoConverter videoContentToCatalogItemDtoConverter) {
        this.videoContentToCatalogItemDtoConverter = videoContentToCatalogItemDtoConverter;
    }

    @Override
    public CatalogItemPageDto convert(Page<VideoContent> object) {
        var result = new CatalogItemPageDto();
        result.setTotalPages(object.getTotalPages());
        result.setCatalogItemDtos(getCatalogItemDtos(object));
        return result;
    }

    private Collection<CatalogItemDto> getCatalogItemDtos(Page<VideoContent> object) {
        return object.getContent().stream()
                .map(videoContentToCatalogItemDtoConverter::convert)
                .toList();
    }
}
