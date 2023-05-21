package by.bsuir.linguaserver.facade;

import by.bsuir.linguaserver.converter.impl.DictionaryWordToDictionaryWordDtoConverter;
import by.bsuir.linguaserver.dto.DictionaryWordDto;
import by.bsuir.linguaserver.entity.Dictionary;
import by.bsuir.linguaserver.entity.DictionaryWord;
import by.bsuir.linguaserver.repository.DictionaryRepository;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class TrainingFacade {

    private final DictionaryRepository dictionaryRepository;
    private final DictionaryWordToDictionaryWordDtoConverter dictionaryWordToDictionaryWordDtoConverter;

    public TrainingFacade(DictionaryRepository dictionaryRepository, DictionaryWordToDictionaryWordDtoConverter dictionaryWordToDictionaryWordDtoConverter) {
        this.dictionaryRepository = dictionaryRepository;
        this.dictionaryWordToDictionaryWordDtoConverter = dictionaryWordToDictionaryWordDtoConverter;
    }

    public List<DictionaryWordDto> prepareTraining(Long dictionaryId,
                                                   Integer size) {
        Dictionary dictionary = dictionaryRepository.findById(dictionaryId).orElseThrow();
        ArrayList<DictionaryWord> dictionaryWords = new ArrayList<>(dictionary.getDictionaryWords());
        size = size != null ? Math.min(size, dictionaryWords.size()) : dictionaryWords.size();
        return selectWordsForTraining(dictionaryWords, size).stream()
                .map(dictionaryWordToDictionaryWordDtoConverter::convert)
                .toList();
    }

    private List<DictionaryWord> selectWordsForTraining(ArrayList<DictionaryWord> dictionaryWords,
                                                        Integer size) {
        // TODO sort
        return dictionaryWords.subList(0, size);
    }
}
