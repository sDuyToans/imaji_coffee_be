package com.duytoan.imajicoffee.imaji_coffee_be.config;

import com.duytoan.imajicoffee.imaji_coffee_be.utils.JwtCookieHandshakeInterceptor;
import com.duytoan.imajicoffee.imaji_coffee_be.utils.UserHandshakeHandler;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    private final JwtCookieHandshakeInterceptor interceptor;
    private final UserHandshakeHandler handshakeHandler;

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/topic", "/queue");
        registry.setApplicationDestinationPrefixes("/app");
        registry.setUserDestinationPrefix("/user");
//        WebSocketMessageBrokerConfigurer.super.configureMessageBroker(registry);
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws")
                .setAllowedOriginPatterns("http://localhost:5173")
                .addInterceptors(interceptor)
                .setHandshakeHandler(handshakeHandler)
                .withSockJS();
//        WebSocketMessageBrokerConfigurer.super.registerStompEndpoints(registry);
    }
}
