package by.bsuir.linguaserver.controller;

import by.bsuir.linguaserver.dto.PlayerMessageDto;
import by.bsuir.linguaserver.service.DuoWatchRequestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.security.Principal;

@Controller
@MessageMapping("/screening-room/{screeningRoomId}")
@Slf4j
public class ScreeningRoomController {

    private final SimpMessagingTemplate simpMessagingTemplate;
    private final DuoWatchRequestService duoWatchRequestService;

    public ScreeningRoomController(SimpMessagingTemplate simpMessagingTemplate,
                                   DuoWatchRequestService duoWatchRequestService) {
        this.simpMessagingTemplate = simpMessagingTemplate;
        this.duoWatchRequestService = duoWatchRequestService;
    }

    @MessageMapping
    public void handleMessage(@Payload PlayerMessageDto playerMessage,
                              @DestinationVariable Long screeningRoomId,
                              Principal principal) {
        log.info(principal.getName() + ": new message " + playerMessage.getMessageType());
        log.info(principal.getName() + ": new payload " + playerMessage.getPayload());
        duoWatchRequestService.getPartner(screeningRoomId, principal.getName()).ifPresent(partner -> {
            String destination = "/reply/screening-room/" + screeningRoomId;
            log.info("send to " + partner.getUsername() + " on " + destination);
            simpMessagingTemplate.convertAndSendToUser(partner.getUsername(), destination, playerMessage);
        });
    }
}
