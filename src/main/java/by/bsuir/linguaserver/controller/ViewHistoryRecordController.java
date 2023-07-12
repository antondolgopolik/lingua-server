package by.bsuir.linguaserver.controller;

import by.bsuir.linguaserver.entity.User;
import by.bsuir.linguaserver.entity.VideoContent;
import by.bsuir.linguaserver.entity.VideoContentLoc;
import by.bsuir.linguaserver.entity.ViewHistoryRecord;
import by.bsuir.linguaserver.repository.UserRepository;
import by.bsuir.linguaserver.repository.VideoContentLocRepository;
import by.bsuir.linguaserver.repository.VideoContentRepository;
import by.bsuir.linguaserver.repository.ViewHistoryRecordRepository;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.UUID;

@RestController
@RequestMapping("/api/view-history-record")
public class ViewHistoryRecordController {

    private final UserRepository userRepository;
    private final VideoContentRepository videoContentRepository;
    private final VideoContentLocRepository videoContentLocRepository;
    private final ViewHistoryRecordRepository viewHistoryRecordRepository;

    public ViewHistoryRecordController(UserRepository userRepository, VideoContentRepository videoContentRepository, VideoContentLocRepository videoContentLocRepository, ViewHistoryRecordRepository viewHistoryRecordRepository) {
        this.userRepository = userRepository;
        this.videoContentRepository = videoContentRepository;
        this.videoContentLocRepository = videoContentLocRepository;
        this.viewHistoryRecordRepository = viewHistoryRecordRepository;
    }

    @PostMapping("/{videoContentLocId}")
    public void create(@PathVariable String videoContentLocId,
                       Authentication authentication) {
        User client = userRepository.findByUsername(authentication.getName()).orElseThrow();
        UUID uuid = UUID.fromString(videoContentLocId);
        VideoContentLoc videoContentLoc = videoContentLocRepository.findById(uuid).orElseThrow();

        VideoContent videoContent = videoContentLoc.getVideoContent();
        videoContent.setViews(videoContent.getViews() + 1);
        videoContentRepository.save(videoContent);

        ViewHistoryRecord viewHistoryRecord = new ViewHistoryRecord();
        viewHistoryRecord.setClient(client);
        viewHistoryRecord.setVideoContentLoc(videoContentLoc);
        viewHistoryRecord.setCreatedAt(LocalDate.now());
        viewHistoryRecordRepository.save(viewHistoryRecord);
    }
}
