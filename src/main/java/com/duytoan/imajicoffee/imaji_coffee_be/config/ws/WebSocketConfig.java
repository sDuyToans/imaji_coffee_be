package com.duytoan.imajicoffee.imaji_coffee_be.config.ws;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

/**
 * Web Socket Config
 * @author duytoan
 * @since 01/13/2026
 */
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    /**
     * enableSimpleBroker -> enable a simple message broker that allow clients to subscribe and receive messages
     * from the specific prefix destination which is "/api/v1/chat/topic"
     * setApplicationDestinationPrefixes -> set the application destination prefix
     * Summarize: we send message to "/api/v1/chat/topic, and we subscribe/listen for messages to prefix "/api/v1/chat/topic"
     */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/api/v1/topic");
        config.setApplicationDestinationPrefixes("/api/v1/app");
    }

    /**
     * Register a Stomp (Simple Text Oriented Message Protocol) endpoint that client can connect to
     * addEndpoint -> register the root endpoint as the WebSocket endpoint
     * withSockJS -> enable sockJS support -> is a JS library which provides WebSocket interface
     * for browsers that don't support WebSocket
     */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/api/v1")
                .setAllowedOrigins("http://localhost:5173")
                .withSockJS();
    }
}
