package by.bsuir.linguaserver.converter.impl;

import by.bsuir.linguaserver.converter.Converter;
import by.bsuir.linguaserver.dto.CatalogItemPageDto;
import by.bsuir.linguaserver.entity.VideoContent;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
public class VideoContentPageToCatalogItemPageDtoConverter implements Converter<Page<VideoContent>, CatalogItemPageDto> {

    private final VideoContentToCatalogItemDtoConverter videoContentToCatalogItemDtoConverter;

    public VideoContentPageToCatalogItemPageDtoConverter(VideoContentToCatalogItemDtoConverter videoContentToCatalogItemDtoConverter) {
        this.videoContentToCatalogItemDtoConverter = videoContentToCatalogItemDtoConverter;
    }

    @Override
    public CatalogItemPageDto convert(Page<VideoContent> object) {
        CatalogItemPageDto catalogItemPageDto = new CatalogItemPageDto();
        catalogItemPageDto.setTotalPages(object.getTotalPages());
        catalogItemPageDto.setCatalogItemDtos(object.getContent().stream()
                .map(videoContentToCatalogItemDtoConverter::convert)
                .toList());
        return catalogItemPageDto;
    }
}
