package by.bsuir.linguaserver.job;

import lombok.Data;

import java.util.List;

@Data
public class VideoContentImportData {
    private String name;
    private String shortDescription;
    private String description;
    private Integer duration;
    private List<String> genres;
    private List<String> videoContentLocs;
}
