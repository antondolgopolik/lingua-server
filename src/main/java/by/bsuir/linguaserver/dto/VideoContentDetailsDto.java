package by.bsuir.linguaserver.dto;

import by.bsuir.linguaserver.entity.Genre;
import by.bsuir.linguaserver.entity.VideoContentLoc;
import lombok.Data;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Data
public class VideoContentDetailsDto {

    private UUID id;
    private String name;
    private String description;
    private Integer duration;
    private Long views;
    private Collection<Genre> genres;
    private Collection<VideoContentLoc> videoContentLocs;

}
