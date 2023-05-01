package by.bsuir.linguaserver.dto;

import lombok.Data;

import java.util.List;

@Data
public class CatalogItemPageDto {

    private Integer totalPages;
    private List<CatalogItemDto> catalogItemDtos;

}
