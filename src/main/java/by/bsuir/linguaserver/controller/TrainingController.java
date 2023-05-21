package by.bsuir.linguaserver.controller;

import by.bsuir.linguaserver.dto.DictionaryWordDto;
import by.bsuir.linguaserver.facade.TrainingFacade;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/trainings")
@Slf4j
public class TrainingController {

    private final TrainingFacade trainingFacade;

    public TrainingController(TrainingFacade trainingFacade) {
        this.trainingFacade = trainingFacade;
    }

    @GetMapping("/{dictionaryId}")
    public List<DictionaryWordDto> prepareTraining(@PathVariable Long dictionaryId,
                                                   @RequestParam Integer size) {
        return trainingFacade.prepareTraining(dictionaryId, size);
    }
}
