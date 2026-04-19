package com.duytoan.imajicoffee.imaji_coffee_be.controller.chat;

import com.duytoan.imajicoffee.imaji_coffee_be.dto.chat.ChatConversationResponse;
import com.duytoan.imajicoffee.imaji_coffee_be.dto.chat.ChatMessageResponse;
import com.duytoan.imajicoffee.imaji_coffee_be.services.chat.IChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Chat REST Controller
 * @author duytoan
 * @since April 19, 2026
 */
@RestController
@RequestMapping("/api/v1/chat")
@RequiredArgsConstructor
public class ChatRestController {

    private final IChatService chatService;

    /**
     * Create a new chat conversation for a customer
     * @param customerId the ID of the customer
     * @return the created chat conversation response
     */
    @PostMapping("/customer/{customerId}")
    public ChatConversationResponse createConversation(@PathVariable Long customerId) {
        return chatService.createConversation(customerId);
    }

    /**
     * Get all messages for a specific conversation
     * @param conversationId the ID of the conversation
     * @return a list of chat message responses
     */
    @GetMapping("/{conversationId}/messages")
    public List<ChatMessageResponse> getMessages(@PathVariable Long conversationId) {
        return chatService.getConversationMessages(conversationId);
    }

    /**
     * Get all conversations for a specific customer
     * @param customerId the ID of the customer
     * @return a list of chat conversation responses
     */
    @GetMapping("/customer/{customerId}")
    public List<ChatConversationResponse> getCustomerConversations(@PathVariable Long customerId) {
        return chatService.getCustomerConversations(customerId);
    }

    /**
     * Get all conversations for a specific admin
     * @param adminId the ID of the admin
     * @return a list of chat conversation responses
     */
    @GetMapping("/admin/{adminId}")
    public List<ChatConversationResponse> getAdminConversations(@PathVariable Long adminId){
        return chatService.getAdminConversations(adminId);
    }
}
