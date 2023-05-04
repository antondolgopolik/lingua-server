package by.bsuir.linguaserver.controller;

import by.bsuir.linguaserver.converter.impl.VideoContentPageToCatalogItemPageDtoConverter;
import by.bsuir.linguaserver.converter.impl.VideoContentToVideoContentDetailsDtoConverter;
import by.bsuir.linguaserver.dto.CatalogItemPageDto;
import by.bsuir.linguaserver.dto.VideoContentDetailsDto;
import by.bsuir.linguaserver.repository.VideoContentRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/video-content")
public class VideoContentController {

    private final VideoContentRepository videoContentRepository;
    private final VideoContentPageToCatalogItemPageDtoConverter videoContentPageToCatalogItemPageDtoConverter;
    private final VideoContentToVideoContentDetailsDtoConverter videoContentToVideoContentDetailsDtoConverter;

    public VideoContentController(VideoContentRepository videoContentRepository,
                                  VideoContentPageToCatalogItemPageDtoConverter videoContentPageToCatalogItemPageDtoConverter,
                                  VideoContentToVideoContentDetailsDtoConverter videoContentToVideoContentDetailsDtoConverter) {
        this.videoContentRepository = videoContentRepository;
        this.videoContentPageToCatalogItemPageDtoConverter = videoContentPageToCatalogItemPageDtoConverter;
        this.videoContentToVideoContentDetailsDtoConverter = videoContentToVideoContentDetailsDtoConverter;
    }

    @GetMapping("/search")
    public CatalogItemPageDto searchVideoContent(@RequestParam(defaultValue = "") String q,
                                                 @RequestParam(defaultValue = "0") String p,
                                                 @RequestParam(defaultValue = "15") String s) {
        int page = Integer.parseInt(p);
        int size = Integer.parseInt(s);
        PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.DESC, "prevMonthViews");
        return videoContentPageToCatalogItemPageDtoConverter.convert(videoContentRepository.findByNameContainingIgnoreCase(q, pageRequest));
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
