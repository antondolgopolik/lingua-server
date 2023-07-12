package by.bsuir.linguaserver.facade;

import by.bsuir.linguaserver.converter.impl.DictionaryToDictionaryDtoConverter;
import by.bsuir.linguaserver.converter.impl.DictionaryWordToDictionaryWordDtoConverter;
import by.bsuir.linguaserver.dto.AddWordToDictionaryFormDto;
import by.bsuir.linguaserver.dto.DictionaryDto;
import by.bsuir.linguaserver.dto.DictionaryWordDto;
import by.bsuir.linguaserver.entity.Dictionary;
import by.bsuir.linguaserver.entity.DictionaryWord;
import by.bsuir.linguaserver.repository.DictionaryRepository;
import by.bsuir.linguaserver.repository.DictionaryWordRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DictionaryFacade {

    private final DictionaryRepository dictionaryRepository;
    private final DictionaryWordRepository dictionaryWordRepository;
    private final DictionaryToDictionaryDtoConverter dictionaryToDictionaryDtoConverter;
    private final DictionaryWordToDictionaryWordDtoConverter dictionaryWordToDictionaryWordDtoConverter;

    public DictionaryFacade(DictionaryRepository dictionaryRepository,
                            DictionaryWordRepository dictionaryWordRepository,
                            DictionaryToDictionaryDtoConverter dictionaryToDictionaryDtoConverter,
                            DictionaryWordToDictionaryWordDtoConverter dictionaryWordToDictionaryWordDtoConverter) {
        this.dictionaryRepository = dictionaryRepository;
        this.dictionaryWordRepository = dictionaryWordRepository;
        this.dictionaryToDictionaryDtoConverter = dictionaryToDictionaryDtoConverter;
        this.dictionaryWordToDictionaryWordDtoConverter = dictionaryWordToDictionaryWordDtoConverter;
    }

    public List<DictionaryDto> getUserDictionaries(String username) {
        return dictionaryRepository.findAllByOwnerUsername(username).stream()
                .map(dictionaryToDictionaryDtoConverter::convert)
                .toList();
    }

    public List<DictionaryWordDto> getDictionaryWords(Long dictionaryId,
                                                      String username) {
        return dictionaryWordRepository.findByDictionaryIdAndDictionaryOwnerUsername(dictionaryId, username).stream()
                .map(dictionaryWordToDictionaryWordDtoConverter::convert)
                .toList();
    }

    public void addWordToDictionary(Long dictionaryId,
                                    AddWordToDictionaryFormDto formDto) {
        Dictionary dictionary = dictionaryRepository.findById(dictionaryId).orElseThrow();
        DictionaryWord dictionaryWord = new DictionaryWord();
        dictionaryWord.setDictionary(dictionary);
        dictionaryWord.setFirstLanguageText(formDto.getFirstLanguageText());
        dictionaryWord.setSecondLanguageText(formDto.getSecondLanguageText());
        dictionaryWord.setTranscription(formDto.getTranscription());
        dictionaryWordRepository.save(dictionaryWord);
        dictionary.getDictionaryWords().add(dictionaryWord);
        dictionaryRepository.save(dictionary);
    }
}
