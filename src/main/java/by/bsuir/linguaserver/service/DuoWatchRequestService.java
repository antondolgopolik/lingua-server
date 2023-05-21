package by.bsuir.linguaserver.service;

import by.bsuir.linguaserver.entity.DuoWatchResponse;
import by.bsuir.linguaserver.entity.User;
import by.bsuir.linguaserver.repository.DuoWatchRequestRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DuoWatchRequestService {

    private final DuoWatchRequestRepository duoWatchRequestRepository;

    public DuoWatchRequestService(DuoWatchRequestRepository duoWatchRequestRepository) {
        this.duoWatchRequestRepository = duoWatchRequestRepository;
    }

    public Optional<User> getPartner(Long duoWatchRequestId, String username) {
        return duoWatchRequestRepository.findById(duoWatchRequestId).map(duoWatchRequest -> {
            User requester = duoWatchRequest.getRequester();
            User responder = duoWatchRequest.getDuoWatchResponses().stream()
                    .filter(DuoWatchResponse::getActive)
                    .findAny()
                    .map(DuoWatchResponse::getResponder)
                    .orElse(null);
            if (requester.getUsername().equals(username)) {
                return responder;
            }
            if (responder != null && responder.getUsername().equals(username)) {
                return requester;
            }
            return null;
        });
    }
}
