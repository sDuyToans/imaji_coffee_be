package com.duytoan.imajicoffee.imaji_coffee_be.controller.chat.websocket;

import com.duytoan.imajicoffee.imaji_coffee_be.dto.chat.websocket.ChatMessageRequest;
import com.duytoan.imajicoffee.imaji_coffee_be.dto.chat.websocket.ChatMessageResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.security.Principal;
import java.time.Instant;

/**
 * @author duytoan
 * @since 01/13/2026
 */
@Controller
@RequiredArgsConstructor
@Log4j2
public class ChatWsController {

    private final SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/chat.private")
    public void sendPrivate(@Valid ChatMessageRequest request, Principal principal) {

        String from = principal.getName(); // set by handshake principal resolver
        String to = request.getTo();

        ChatMessageResponse payload = ChatMessageResponse.builder()
                .from(from)
                .to(to)
                .content(request.getContent())
                .timestamp(Instant.now())
                .build();

        log.info(payload);

        // Send to receiver box
        messagingTemplate.convertAndSendToUser(to,"/queue/messages", payload);

        // also send back to sender inbox so sender sees it immediately
        messagingTemplate.convertAndSendToUser(from, "/queue/messages", payload);

    }

    // Admin broadcast
    // Client sends to /app/chat.announce
    // Everyone subscribes to /topic/announcements
    @MessageMapping("/chat.announce")
    public void announce(@Valid ChatMessageRequest req, Principal principal) {

        ChatMessageResponse payload = ChatMessageResponse.builder()
                .from(principal.getName())
                .to(null)
                .content(req.getContent())
                .timestamp(Instant.now())
                .build();

        messagingTemplate.convertAndSend("/topic/announcements", payload);

    }
}
