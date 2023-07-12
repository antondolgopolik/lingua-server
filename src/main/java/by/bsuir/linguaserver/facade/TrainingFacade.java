package by.bsuir.linguaserver.facade;

import by.bsuir.linguaserver.converter.impl.DictionaryWordToDictionaryWordDtoConverter;
import by.bsuir.linguaserver.dto.DictionaryWordDto;
import by.bsuir.linguaserver.dto.TrainingAnswerDto;
import by.bsuir.linguaserver.entity.Dictionary;
import by.bsuir.linguaserver.entity.DictionaryWord;
import by.bsuir.linguaserver.repository.DictionaryRepository;
import by.bsuir.linguaserver.repository.DictionaryWordRepository;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Component
public class TrainingFacade {

    private final DictionaryRepository dictionaryRepository;
    private final DictionaryWordRepository dictionaryWordRepository;
    private final DictionaryWordToDictionaryWordDtoConverter dictionaryWordToDictionaryWordDtoConverter;

    public TrainingFacade(DictionaryRepository dictionaryRepository,
                          DictionaryWordRepository dictionaryWordRepository,
                          DictionaryWordToDictionaryWordDtoConverter dictionaryWordToDictionaryWordDtoConverter) {
        this.dictionaryRepository = dictionaryRepository;
        this.dictionaryWordRepository = dictionaryWordRepository;
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
        return dictionaryWords.stream()
                .sorted((o1, o2) -> {
                    int r1 = o1.getRepetitions();
                    if (r1 == 0) {
                        return -1;
                    }
                    int r2 = o2.getRepetitions();
                    if (r2 == 0) {
                        return 1;
                    }
                    return Integer.compare(o1.getCorrectRepetitions() * r2, o2.getCorrectRepetitions() * r2);
                })
                .limit(size)
                .toList();
    }

    public void saveTrainingResult(List<TrainingAnswerDto> trainingAnswerDtos) {
        for (TrainingAnswerDto trainingAnswerDto : trainingAnswerDtos) {
            DictionaryWord dictionaryWord = dictionaryWordRepository.findById(trainingAnswerDto.getDictionaryWordId()).orElseThrow();
            dictionaryWord.setRepetitions(dictionaryWord.getRepetitions() + 1);
            dictionaryWord.setCorrectRepetitions(dictionaryWord.getCorrectRepetitions() + (trainingAnswerDto.getCorrect() ? 1 : 0));
            dictionaryWordRepository.save(dictionaryWord);
        }
    }
}
