package by.bsuir.linguaserver.controller;

import by.bsuir.linguaserver.dto.CreateDuoWatchRequestFormDto;
import by.bsuir.linguaserver.dto.DuoWatchRequestCatalogItemPageDto;
import by.bsuir.linguaserver.facade.DuoWatchRequestFacade;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/duo-watch-request")
public class DuoWatchRequestController {

    private final DuoWatchRequestFacade duoWatchRequestFacade;

    public DuoWatchRequestController(DuoWatchRequestFacade duoWatchRequestFacade) {
        this.duoWatchRequestFacade = duoWatchRequestFacade;
    }

    @PostMapping("/create")
    public void createDuoWatchRequest(@RequestBody CreateDuoWatchRequestFormDto createDuoWatchRequestFormDto,
                                      Authentication authentication) {
        String username = authentication.getName();
        UUID videoContentLocId = createDuoWatchRequestFormDto.getVideoContentLocId();
        long secondLanguageId = createDuoWatchRequestFormDto.getSecondLangId();
        duoWatchRequestFacade.createDuoWatchRequest(username, videoContentLocId, secondLanguageId);
    }

    @GetMapping("/search")
    public DuoWatchRequestCatalogItemPageDto searchDuoWatchRequests(@RequestParam(defaultValue = "") String q,
                                                                    @RequestParam(required = false) Long videoContentLang,
                                                                    @RequestParam(required = false) Long secondLang,
                                                                    @RequestParam(defaultValue = "0") Integer p,
                                                                    @RequestParam(defaultValue = "15") Integer s,
                                                                    Authentication authentication) {
        return duoWatchRequestFacade.searchDuoWatchRequests(authentication.getName(), q, videoContentLang, secondLang, p, s);
    }

    @PostMapping("/{duoWatchRequestId}/accept")
    public void acceptDuoWatchRequest(@PathVariable Long duoWatchRequestId,
                                      Authentication authentication) {
        duoWatchRequestFacade.acceptDuoWatchRequest(authentication.getName(), duoWatchRequestId);
    }
}
