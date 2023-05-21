package by.bsuir.linguaserver.controller;

import by.bsuir.linguaserver.entity.Subtitle;
import by.bsuir.linguaserver.repository.SubtitleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/subtitle")
@Slf4j
public class SubtitleController {

    private final SubtitleRepository subtitleRepository;

    public SubtitleController(SubtitleRepository subtitleRepository) {
        this.subtitleRepository = subtitleRepository;
    }

    @GetMapping
    public ResponseEntity<Subtitle> getSubtitle(@RequestParam UUID videoContentLocId,
                                                @RequestParam Long languageId) {
        return subtitleRepository.findBy(videoContentLocId, languageId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }
}
