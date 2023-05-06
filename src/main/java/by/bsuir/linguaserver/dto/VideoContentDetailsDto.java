package by.bsuir.linguaserver.dto;

import by.bsuir.linguaserver.entity.Genre;
import by.bsuir.linguaserver.entity.Language;
import by.bsuir.linguaserver.entity.Subtitle;
import lombok.Data;

import java.util.Collection;
import java.util.UUID;

@Data
public class VideoContentDetailsDto {
    private UUID id;
    private String name;
    private String description;
    private Integer duration;
    private Long views;
    private Collection<Genre> genres;
    private Collection<VideoContentLocDto> videoContentLocDtos;

    @Data
    public static class VideoContentLocDto {
        private UUID id;
        private Language language;
        private Collection<Subtitle> subtitles;
    }
}
