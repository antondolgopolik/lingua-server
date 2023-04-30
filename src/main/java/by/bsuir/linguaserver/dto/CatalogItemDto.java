package by.bsuir.linguaserver.dto;

import lombok.Data;

import java.util.List;

@Data
public class CatalogItemDto {

    private String id;
    private String name;
    private Integer duration;
    private Long views;
    private List<String> genres;

}
