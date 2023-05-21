package by.bsuir.linguaserver.converter.impl;

import by.bsuir.linguaserver.converter.Converter;
import by.bsuir.linguaserver.dto.DictionaryDto;
import by.bsuir.linguaserver.entity.Dictionary;
import org.springframework.stereotype.Component;

@Component
public class DictionaryToDictionaryDtoConverter implements Converter<Dictionary, DictionaryDto> {

    @Override
    public DictionaryDto convert(Dictionary object) {
        var result = new DictionaryDto();
        result.setId(object.getId());
        result.setName(object.getName());
        result.setFirstLanguage(object.getFirstLanguage());
        result.setSecondLanguage(object.getSecondLanguage());
        result.setSize(object.getDictionaryWords().size());
        return result;
    }
}
