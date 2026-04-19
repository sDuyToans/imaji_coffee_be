package com.duytoan.imajicoffee.imaji_coffee_be.controller.chat;

import com.duytoan.imajicoffee.imaji_coffee_be.dto.chat.ChatMessageRequest;
import com.duytoan.imajicoffee.imaji_coffee_be.dto.chat.ChatMessageResponse;
import com.duytoan.imajicoffee.imaji_coffee_be.services.chat.IChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

/**
 * Chat Controller
 * @author duytoan
 * @since 01/13/2026
 */
@Slf4j
@Controller
@RequiredArgsConstructor
public class ChatController {

    private final IChatService chatService;

    /**
     * Handle incoming chat messages and broadcast them to subscribers
     * @param conversationId the ID of the conversation
     * @param request the chat message request containing the message content
     * @return the saved chat message response to be sent to subscribers
     */
    @MessageMapping("/chat/{chatId}")
    @SendTo("/api/v1/topic/chat/{chatId}")
    public ChatMessageResponse sendMessage(
            @DestinationVariable Long conversationId,
            ChatMessageRequest request
    ) {
        log.info("NEW MESSAGE RECEIVED FOR CONVERSATION ID: {}", conversationId);
        return chatService.saveMessage(conversationId, request);
    }
}
