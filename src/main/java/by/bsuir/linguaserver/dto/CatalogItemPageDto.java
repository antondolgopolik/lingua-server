package by.bsuir.linguaserver.dto;

import lombok.Data;

import java.util.Collection;

@Data
public class CatalogItemPageDto {
    private Integer totalPages;
    private Collection<CatalogItemDto> catalogItemDtos;
}
