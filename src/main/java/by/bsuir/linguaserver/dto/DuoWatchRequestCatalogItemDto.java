package by.bsuir.linguaserver.dto;

import by.bsuir.linguaserver.entity.Language;
import lombok.Data;

import java.util.UUID;

@Data
public class DuoWatchRequestCatalogItemDto {
    private Long id;
    private VideoContentLocDto videoContentLocDto;
    private Language secondLanguage;

    @Data
    public static class VideoContentLocDto {
        private UUID id;
        private CatalogItemDto catalogItemDto;
        private Language language;
    }
}
