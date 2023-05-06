package by.bsuir.linguaserver.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class CreateDuoWatchRequestFormDto {
    private UUID videoContentLocId;
    private Long secondLangId;
}
