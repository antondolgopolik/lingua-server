package by.bsuir.linguaserver.converter.impl;

import by.bsuir.linguaserver.converter.Converter;
import by.bsuir.linguaserver.dto.DictionaryWordDto;
import by.bsuir.linguaserver.entity.DictionaryWord;
import org.springframework.stereotype.Component;

@Component
public class DictionaryWordToDictionaryWordDtoConverter implements Converter<DictionaryWord, DictionaryWordDto> {

    @Override
    public DictionaryWordDto convert(DictionaryWord object) {
        var result = new DictionaryWordDto();
        result.setId(object.getId());
        result.setFirstLanguageText(object.getFirstLanguageText());
        result.setSecondLanguageText(object.getSecondLanguageText());
        result.setTranscription(object.getTranscription());
        result.setMastery(getMastery(object));
        return result;
    }

    private int getMastery(DictionaryWord object) {
        if (object.getRepetitions() == 0) {
            return 0;
        }
        float temp = object.getCorrectRepetitions();
        temp = temp / object.getRepetitions() * 100;
        return (int) temp;
    }
}
