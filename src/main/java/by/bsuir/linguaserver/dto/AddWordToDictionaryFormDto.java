package by.bsuir.linguaserver.dto;

import lombok.Data;

@Data
public class AddWordToDictionaryFormDto {
    private Long dictionaryId;
    private String firstLanguageText;
    private String secondLanguageText;
    private String transcription;
}
