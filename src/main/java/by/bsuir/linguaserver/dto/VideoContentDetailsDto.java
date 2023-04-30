package by.bsuir.linguaserver.dto;

import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class VideoContentDetailsDto {

    private UUID id;
    private String name;
    private String description;
    private Integer duration;
    private Long views;
    private List<String> genres;

}
