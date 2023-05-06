package by.bsuir.linguaserver.controller;

import by.bsuir.linguaserver.entity.Language;
import by.bsuir.linguaserver.repository.LanguageRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/language")
public class LanguageController {

    private final LanguageRepository languageRepository;

    public LanguageController(LanguageRepository languageRepository) {
        this.languageRepository = languageRepository;
    }

    @GetMapping("/all")
    public List<Language> getAllLanguages() {
        return languageRepository.findAll();
    }
}
