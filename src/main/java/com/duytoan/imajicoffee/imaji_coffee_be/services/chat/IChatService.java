package com.duytoan.imajicoffee.imaji_coffee_be.services.chat;

import com.duytoan.imajicoffee.imaji_coffee_be.dto.chat.ChatConversationResponse;
import com.duytoan.imajicoffee.imaji_coffee_be.dto.chat.ChatMessageRequest;
import com.duytoan.imajicoffee.imaji_coffee_be.dto.chat.ChatMessageResponse;

import java.util.List;

/**
 * Service interface for managing chat conversations and messages.
 * @author Duy Toan
 * @since April 19, 2026
 */
public interface IChatService {

    /**
     * Creates a new chat conversation for a given customer.
     */
    ChatConversationResponse createConversation(Long customerId);

    /**
     * Saves a new chat message to a specific conversation.
     */
    ChatMessageResponse saveMessage(Long conversationId, ChatMessageRequest request);

    /**
     * Retrieves all messages for a specific conversation, ordered by creation time.
     */
    List<ChatMessageResponse> getConversationMessages(Long conversationId);

    /**
     * Retrieves all conversations for a specific customer, ordered by last updated time.
     */
    List<ChatConversationResponse> getCustomerConversations(Long customerId);

    /**
     * Retrieves all conversations assigned to a specific admin, ordered by last updated time.
     */
    List<ChatConversationResponse> getAdminConversations(Long adminId);
}
