package com.duytoan.imajicoffee.imaji_coffee_be.controller.chat;

import com.duytoan.imajicoffee.imaji_coffee_be.entities.chat.ChatMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

/**
 * Chat Controller
 * @author duytoan
 * @since 01/13/2026
 */
@Controller
public class ChatController {

    /**
     * Handles the "/chat.register" message mapping by setting the username in the session attributes
     * and returning the received ChatMessage object.
     */
    @MessageMapping("/chat.register")
    @SendTo("/topic/public")
    public ChatMessage register(@Payload ChatMessage chatMessage, SimpMessageHeaderAccessor headerAccessor) {
        if (headerAccessor.getSessionAttributes() != null) {
            headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());
        }
        return chatMessage; // Broadcast the registration message to all subscribers
    }

    /**
     * Handles the "/chat.send" message mapping by simply returning the received ChatMessage object,
     * which will then be sent to all subscribers of the "/topic/public" destination.
     */
    @MessageMapping("/chat.send")
    @SendTo("/topic/public")
    public ChatMessage sendMessage(@Payload ChatMessage chatMessage) {
        return chatMessage; // Broadcast the chat message to all subscribers
    }
}
