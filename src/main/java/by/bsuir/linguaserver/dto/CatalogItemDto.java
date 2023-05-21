package by.bsuir.linguaserver.dto;

import by.bsuir.linguaserver.entity.Genre;
import lombok.Data;

import java.util.Collection;

@Data
public class CatalogItemDto {
    private String id;
    private String name;
    private String shortDescription;
    private Integer duration;
    private Long views;
    private Collection<Genre> genres;
}
