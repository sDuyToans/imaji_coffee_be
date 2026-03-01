package com.duytoan.imajicoffee.imaji_coffee_be.utils;

import lombok.NonNull;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

import java.security.Principal;
import java.util.Map;

/**
 * @author duytoan
 * @since 01/13/2026
 */
@Component
public class UserHandshakeHandler extends DefaultHandshakeHandler {

    @Override
    protected Principal determineUser(@NonNull ServerHttpRequest request,
                                      @NonNull WebSocketHandler wsHandler,
                                      Map<String, Object> attributes) {

        String username = (String) attributes.get("username");
        if (username == null) return null;

        return () -> username;  // Principal.getName();

    }
}
