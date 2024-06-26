package by.bsuir.linguaserver.dto;

import by.bsuir.linguaserver.entity.Genre;
import lombok.Data;

import java.util.Collection;
import java.util.UUID;

@Data
public class CatalogItemDto {
    private UUID id;
    private String name;
    private String shortDescription;
    private Integer duration;
    private Long views;
    private Collection<Genre> genres;
}
