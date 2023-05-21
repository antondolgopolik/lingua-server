package by.bsuir.linguaserver.controller;

import by.bsuir.linguaserver.dto.CreateDuoWatchRequestFormDto;
import by.bsuir.linguaserver.dto.DuoWatchRequestCatalogItemPageDto;
import by.bsuir.linguaserver.dto.PersonalDuoWatchRequestPageDto;
import by.bsuir.linguaserver.entity.DuoWatchRequestStatus;
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

    @GetMapping("/catalog/search")
    public DuoWatchRequestCatalogItemPageDto catalogSearchDuoWatchRequests(@RequestParam(defaultValue = "") String q,
                                                                           @RequestParam(required = false) Long videoContentLang,
                                                                           @RequestParam(required = false) Long secondLang,
                                                                           @RequestParam(defaultValue = "0") Integer p,
                                                                           @RequestParam(defaultValue = "15") Integer s,
                                                                           Authentication authentication) {
        return duoWatchRequestFacade.catalogSearchDuoWatchRequests(authentication.getName(), q, videoContentLang, secondLang, p, s);
    }

    @GetMapping("/personal/search")
    public PersonalDuoWatchRequestPageDto personalSearchDuoWatchRequests(@RequestParam(defaultValue = "") String q,
                                                                         @RequestParam(defaultValue = "true") Boolean owner,
                                                                         @RequestParam(required = false) DuoWatchRequestStatus status,
                                                                         @RequestParam(defaultValue = "0") Integer p,
                                                                         @RequestParam(defaultValue = "15") Integer s,
                                                                         Authentication authentication) {
        return duoWatchRequestFacade.personalSearchDuoWatchRequests(authentication.getName(), q, owner, status, p, s);
    }

    @PostMapping("/{duoWatchRequestId}/accept")
    public void acceptDuoWatchRequest(@PathVariable Long duoWatchRequestId,
                                      Authentication authentication) {
        duoWatchRequestFacade.acceptDuoWatchRequest(authentication.getName(), duoWatchRequestId);
    }
}
