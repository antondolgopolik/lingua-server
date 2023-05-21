package by.bsuir.linguaserver.controller;

import by.bsuir.linguaserver.dto.AddWordToDictionaryFormDto;
import by.bsuir.linguaserver.dto.DictionaryDto;
import by.bsuir.linguaserver.dto.DictionaryWordDto;
import by.bsuir.linguaserver.facade.DictionaryFacade;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/dictionaries")
@Slf4j
public class DictionaryController {

    private final DictionaryFacade dictionaryFacade;

    public DictionaryController(DictionaryFacade dictionaryFacade) {
        this.dictionaryFacade = dictionaryFacade;
    }

    @GetMapping
    public List<DictionaryDto> getUserDictionaries(Principal principal) {
        return dictionaryFacade.getUserDictionaries(principal.getName());
    }

    @GetMapping("/{dictionaryId}")
    public List<DictionaryWordDto> getDictionaryWords(@PathVariable Long dictionaryId,
                                                      Principal principal) {
        return dictionaryFacade.getDictionaryWords(dictionaryId, principal.getName());
    }

    @PostMapping("/add")
    public void addWordToDictionary(@RequestBody AddWordToDictionaryFormDto formDto) {
        dictionaryFacade.addWordToDictionary(formDto);
    }
}
