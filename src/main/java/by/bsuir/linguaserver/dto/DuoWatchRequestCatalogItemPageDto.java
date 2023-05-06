package by.bsuir.linguaserver.dto;

import lombok.Data;

import java.util.Collection;

@Data
public class DuoWatchRequestCatalogItemPageDto {
    private Integer totalPages;
    private Collection<DuoWatchRequestCatalogItemDto> duoWatchRequestCatalogItemDtos;
}
