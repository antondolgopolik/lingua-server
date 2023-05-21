package by.bsuir.linguaserver.dto;

import by.bsuir.linguaserver.entity.Language;
import lombok.Data;

@Data
public class DictionaryDto {
    private Long id;
    private String name;
    private Language firstLanguage;
    private Language secondLanguage;
    private Integer size;
}
