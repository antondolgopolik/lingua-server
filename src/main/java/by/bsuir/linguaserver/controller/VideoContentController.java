package by.bsuir.linguaserver.controller;

import by.bsuir.linguaserver.converter.impl.VideoContentToCatalogItemDtoConverter;
import by.bsuir.linguaserver.converter.impl.VideoContentToVideoContentDetailsDtoConverter;
import by.bsuir.linguaserver.dto.CatalogItemDto;
import by.bsuir.linguaserver.dto.VideoContentDetailsDto;
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
    private final VideoContentToVideoContentDetailsDtoConverter videoContentToVideoContentDetailsDtoConverter;

    public VideoContentController(VideoContentRepository videoContentRepository,
                                  VideoContentToCatalogItemDtoConverter videoContentToCatalogItemDtoConverter,
                                  VideoContentToVideoContentDetailsDtoConverter videoContentToVideoContentDetailsDtoConverter) {
        this.videoContentRepository = videoContentRepository;
        this.videoContentToCatalogItemDtoConverter = videoContentToCatalogItemDtoConverter;
        this.videoContentToVideoContentDetailsDtoConverter = videoContentToVideoContentDetailsDtoConverter;
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
    public ResponseEntity<VideoContentDetailsDto> getVideoContent(@PathVariable String videoContentId) {
        UUID uuid = UUID.fromString(videoContentId);
        return videoContentRepository.findById(uuid)
                .map(videoContentToVideoContentDetailsDtoConverter::convert)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }
}
