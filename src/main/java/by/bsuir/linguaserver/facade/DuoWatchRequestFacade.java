package by.bsuir.linguaserver.facade;

import by.bsuir.linguaserver.converter.impl.DuoWatchRequestToDuoWatchRequestCatalogItemDtoConverter;
import by.bsuir.linguaserver.dto.DuoWatchRequestCatalogItemDto;
import by.bsuir.linguaserver.dto.DuoWatchRequestCatalogItemPageDto;
import by.bsuir.linguaserver.entity.*;
import by.bsuir.linguaserver.repository.*;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

@Component
public class DuoWatchRequestFacade {

    private final UserRepository userRepository;
    private final VideoContentLocRepository videoContentLocRepository;
    private final LanguageRepository languageRepository;
    private final DuoWatchRequestRepository duoWatchRequestRepository;
    private final DuoWatchResponseRepository duoWatchResponseRepository;
    private final DuoWatchRequestToDuoWatchRequestCatalogItemDtoConverter duoWatchRequestToDuoWatchRequestCatalogItemDtoConverter;

    public DuoWatchRequestFacade(UserRepository userRepository,
                                 VideoContentLocRepository videoContentLocRepository,
                                 LanguageRepository languageRepository,
                                 DuoWatchRequestRepository duoWatchRequestRepository,
                                 DuoWatchResponseRepository duoWatchResponseRepository,
                                 DuoWatchRequestToDuoWatchRequestCatalogItemDtoConverter duoWatchRequestToDuoWatchRequestCatalogItemDtoConverter) {
        this.userRepository = userRepository;
        this.videoContentLocRepository = videoContentLocRepository;
        this.languageRepository = languageRepository;
        this.duoWatchResponseRepository = duoWatchResponseRepository;
        this.duoWatchRequestRepository = duoWatchRequestRepository;
        this.duoWatchRequestToDuoWatchRequestCatalogItemDtoConverter = duoWatchRequestToDuoWatchRequestCatalogItemDtoConverter;
    }

    public DuoWatchRequest createDuoWatchRequest(String username,
                                                 UUID videoContentLocId,
                                                 Long secondLangId) {
        Optional<User> user = userRepository.findByUsername(username);
        Optional<VideoContentLoc> videoContentLoc = videoContentLocRepository.findById(videoContentLocId);
        Optional<Language> secondLanguage = languageRepository.findById(secondLangId);
        DuoWatchRequest duoWatchRequest = new DuoWatchRequest();
        duoWatchRequest.setRequester(user.orElseThrow());
        duoWatchRequest.setVideoContentLoc(videoContentLoc.orElseThrow());
        duoWatchRequest.setSecondLanguage(secondLanguage.orElseThrow());
        LocalDate now = LocalDate.now();
        duoWatchRequest.setCreatedAt(now);
        duoWatchRequest.setUpdatedAt(now);
        duoWatchRequest.setStatus(DuoWatchRequestStatus.OPEN);
        return duoWatchRequestRepository.save(duoWatchRequest);
    }

    public DuoWatchRequestCatalogItemPageDto searchDuoWatchRequests(String username,
                                                                    String q,
                                                                    Long videoContentLangId,
                                                                    Long secondLangId,
                                                                    Integer page,
                                                                    Integer size) {
        var searchResults = duoWatchRequestRepository.findBy(username, "%" + q + "%", videoContentLangId, secondLangId);
        sort(searchResults);
        var result = new DuoWatchRequestCatalogItemPageDto();
        result.setDuoWatchRequestCatalogItemDtos(buildDuoWatchRequestCatalogItemDtos(searchResults, page, size));
        result.setTotalPages(searchResults.size() / size + (searchResults.size() % size != 0 ? 1 : 0));
        return result;
    }

    private void sort(ArrayList<DuoWatchRequest> duoWatchRequests) {
        // TODO
    }

    private Collection<DuoWatchRequestCatalogItemDto> buildDuoWatchRequestCatalogItemDtos(ArrayList<DuoWatchRequest> searchResults,
                                                                                          Integer page,
                                                                                          Integer size) {
        int start = page * size;
        int end = Math.min(start + size, searchResults.size());
        Collection<DuoWatchRequestCatalogItemDto> result = new ArrayList<>();
        for (int i = start; i < end; i++) {
            result.add(duoWatchRequestToDuoWatchRequestCatalogItemDtoConverter.convert(searchResults.get(i)));
        }
        return result;
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void acceptDuoWatchRequest(String username,
                                      Long duoWatchRequestId) {
        User user = userRepository.findByUsername(username).orElseThrow();

        DuoWatchRequest duoWatchRequest = duoWatchRequestRepository.findById(duoWatchRequestId).orElseThrow();
        duoWatchRequest.setStatus(DuoWatchRequestStatus.ON_HOLD);
        duoWatchRequest = duoWatchRequestRepository.save(duoWatchRequest);

        DuoWatchResponse duoWatchResponse = new DuoWatchResponse();
        duoWatchResponse.setResponder(user);
        duoWatchResponse.setDuoWatchRequest(duoWatchRequest);
        duoWatchResponse.setActive(true);
        duoWatchResponseRepository.save(duoWatchResponse);
    }
}
