package by.bsuir.linguaserver.websocket;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        // Response destination should have one of these prefixes to be sent back
        config.enableSimpleBroker("/reply");
        // Request destination should have this prefix to be handled with @MessageMapping
        config.setApplicationDestinationPrefixes("/app");
        // Clients should subscribe to destinations with this prefix
        config.setUserDestinationPrefix("/private");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws");
    }
}
