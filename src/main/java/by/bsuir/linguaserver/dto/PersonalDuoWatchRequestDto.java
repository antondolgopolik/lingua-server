package by.bsuir.linguaserver.dto;

import by.bsuir.linguaserver.entity.DuoWatchRequestStatus;
import by.bsuir.linguaserver.entity.Language;
import lombok.Data;

import java.util.UUID;

@Data
public class PersonalDuoWatchRequestDto {
    private Long id;
    private VideoContentLocDto videoContentLocDto;
    private Language secondLanguage;
    private DuoWatchRequestStatus status;
    private String partnerTgUsername;
    private Boolean relevanceConfirmationRequired;

    @Data
    public static class VideoContentLocDto {
        private UUID id;
        private CatalogItemDto catalogItemDto;
        private Language language;
    }
}
