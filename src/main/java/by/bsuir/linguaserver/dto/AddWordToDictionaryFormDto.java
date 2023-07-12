package by.bsuir.linguaserver.dto;

import lombok.Data;

@Data
public class AddWordToDictionaryFormDto {
    private String firstLanguageText;
    private String secondLanguageText;
    private String transcription;
}
