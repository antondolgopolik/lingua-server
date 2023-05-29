package by.bsuir.linguaserver.dto;

import by.bsuir.linguaserver.entity.Genre;
import lombok.Data;

import java.util.Collection;
import java.util.UUID;

@Data
public class VideoContentEditFormDto {
    private UUID id;
    private String name;
    private String shortDescription;
    private String description;
    private Integer duration;
    private Collection<Genre> genres;
}
