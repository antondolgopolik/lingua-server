package by.bsuir.linguaserver.controller;

import by.bsuir.linguaserver.converter.impl.VideoContentToCatalogItemDtoConverter;
import by.bsuir.linguaserver.dto.CatalogItemDto;
import by.bsuir.linguaserver.entity.VideoContent;
import by.bsuir.linguaserver.repository.VideoContentRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/videocontent")
public class VideoContentController {

    private final VideoContentRepository videoContentRepository;
    private final VideoContentToCatalogItemDtoConverter videoContentToCatalogItemDtoConverter;

    public VideoContentController(VideoContentRepository videoContentRepository,
                                  VideoContentToCatalogItemDtoConverter videoContentToCatalogItemDtoConverter) {
        this.videoContentRepository = videoContentRepository;
        this.videoContentToCatalogItemDtoConverter = videoContentToCatalogItemDtoConverter;
    }

    @GetMapping("/most-viewed")
    public List<CatalogItemDto> getMostViewedPrevMonth(@RequestParam(defaultValue = "0") String pPage,
                                                       @RequestParam(defaultValue = "15") String pSize) {
        int page = Integer.parseInt(pPage);
        int size = Integer.parseInt(pSize);
        PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.DESC, "prevMonthViews");
        return videoContentRepository.findAll(pageRequest).getContent().stream()
                .map(videoContentToCatalogItemDtoConverter::convert)
                .toList();
    }

    @GetMapping("/{videoContentId}")
    public ResponseEntity<VideoContent> getVideoContent(@PathVariable String videoContentId) {
        UUID uuid = UUID.fromString(videoContentId);
        return videoContentRepository.findById(uuid)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }
}
