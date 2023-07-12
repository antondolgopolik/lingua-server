package by.bsuir.linguaserver.controller;

import by.bsuir.linguaserver.dto.PlayerMessageDto;
import by.bsuir.linguaserver.dto.PlayerMessageType;
import by.bsuir.linguaserver.entity.User;
import by.bsuir.linguaserver.service.DuoWatchRequestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;

import java.security.Principal;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

@Controller
@MessageMapping("/screening-room/{screeningRoomId}")
@Slf4j
public class ScreeningRoomController {

    private final SimpMessagingTemplate simpMessagingTemplate;
    private final DuoWatchRequestService duoWatchRequestService;

    private final Map<String, Long> connectedUsers = new ConcurrentHashMap<>();

    public ScreeningRoomController(SimpMessagingTemplate simpMessagingTemplate,
                                   DuoWatchRequestService duoWatchRequestService) {
        this.simpMessagingTemplate = simpMessagingTemplate;
        this.duoWatchRequestService = duoWatchRequestService;
    }

    @EventListener
    public void handleSessionSubscribe(SessionSubscribeEvent sessionSubscribeEvent) {
        String username = sessionSubscribeEvent.getUser().getName();
        log.info("connected: " + username);
        String dst = StompHeaderAccessor.wrap(sessionSubscribeEvent.getMessage()).getDestination();
        Long screeningRoomId = Long.parseLong(dst.substring(dst.lastIndexOf("/") + 1));
        duoWatchRequestService.getPartner(screeningRoomId, username).ifPresent(partner -> {
            String destination = "/reply/screening-room/" + screeningRoomId;
            PlayerMessageDto playerMessageDto = new PlayerMessageDto();
            if (connectedUsers.containsKey(partner.getUsername())) {
                playerMessageDto.setMessageType(PlayerMessageType.PARTNER_CONNECTED);
                log.info("send PARTNER_CONNECTED to " + partner.getUsername() + " on " + destination);
                simpMessagingTemplate.convertAndSendToUser(partner.getUsername(), destination, playerMessageDto);
            } else {
                playerMessageDto.setMessageType(PlayerMessageType.NO_PLAYER_STATE);
                log.info("send NO_PLAYER_STATE to " + username + " on " + destination);
                simpMessagingTemplate.convertAndSendToUser(username, destination, playerMessageDto);
            }

            connectedUsers.put(username, screeningRoomId);
        });
    }

    @EventListener
    public void handleSessionUnsubscribe(SessionDisconnectEvent sessionDisconnectEvent) {
        String username = sessionDisconnectEvent.getUser().getName();
        log.info("disconnected: " + username);
        Long screeningRoomId = connectedUsers.get(username);
        duoWatchRequestService.getPartner(screeningRoomId, username).ifPresent(partner -> {
            if (connectedUsers.containsKey(partner.getUsername())) {
                String destination = "/reply/screening-room/" + screeningRoomId;
                PlayerMessageDto playerMessageDto = new PlayerMessageDto();
                playerMessageDto.setMessageType(PlayerMessageType.PARTNER_DISCONNECTED);
                log.info("send PARTNER_DISCONNECTED to " + partner.getUsername() + " on " + destination);
                simpMessagingTemplate.convertAndSendToUser(partner.getUsername(), destination, playerMessageDto);
            }

            connectedUsers.remove(username);
        });
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
