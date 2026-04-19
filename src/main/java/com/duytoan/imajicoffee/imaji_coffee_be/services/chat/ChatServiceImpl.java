package com.duytoan.imajicoffee.imaji_coffee_be.services.chat;

import com.duytoan.imajicoffee.imaji_coffee_be.dto.chat.ChatConversationResponse;
import com.duytoan.imajicoffee.imaji_coffee_be.dto.chat.ChatMessageRequest;
import com.duytoan.imajicoffee.imaji_coffee_be.dto.chat.ChatMessageResponse;
import com.duytoan.imajicoffee.imaji_coffee_be.entities.chat.ChatConversation;
import com.duytoan.imajicoffee.imaji_coffee_be.entities.chat.ChatMessage;
import com.duytoan.imajicoffee.imaji_coffee_be.enums.ConversationStatus;
import com.duytoan.imajicoffee.imaji_coffee_be.enums.SenderType;
import com.duytoan.imajicoffee.imaji_coffee_be.enums.SenderTypeDto;
import com.duytoan.imajicoffee.imaji_coffee_be.exceptions.ResourceNotFoundException;
import com.duytoan.imajicoffee.imaji_coffee_be.repository.chat.ChatConversationRepository;
import com.duytoan.imajicoffee.imaji_coffee_be.repository.chat.ChatMessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

/**
 * Implementation of the IChatService interface for managing chat conversations and messages.
 * This service handles the business logic for creating conversations, saving messages,
 * and retrieving conversations and messages for customers and admins.
 * @author Duy Toan
 * @since April 19, 2026
 */
@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements IChatService{

    private final ChatConversationRepository chatConversationRepository;
    private final ChatMessageRepository chatMessageRepository;

    /**
     * Creates a new chat conversation for a given customer.
     *
     * @param customerId
     */
    @Override
    public ChatConversationResponse createConversation(Long customerId) {
        ChatConversation conversation = ChatConversation.builder()
                .customerId(customerId)
                .status(ConversationStatus.OPEN)
                .build();
        ChatConversation saved = chatConversationRepository.save(conversation);
        return toChatConversationResponse(conversation);
    }

    /**
     * Saves a new chat message to a specific conversation.
     *
     * @param conversationId
     * @param request
     */
    @Override
    public ChatMessageResponse saveMessage(Long conversationId, ChatMessageRequest request) {
        ChatConversation conversation = chatConversationRepository.findById(conversationId)
                .orElseThrow(() -> new ResourceNotFoundException("Chat conversation", "Conversation Id", conversationId.toString()));

        ChatMessage message = ChatMessage.builder()
                .conversation(conversation)
                .content(request.getContent())
                .senderId(request.getSenderId())
                .senderName(request.getSenderName())
                .senderType(toEntitySenderType(request.getSenderType()))
                .build();

        ChatMessage savedMessage = chatMessageRepository.save(message);

        conversation.setUpdatedAt(Instant.now());
        chatConversationRepository.save(conversation);

        return toChatMessageResponse(savedMessage);
    }

    /**
     * Retrieves all messages for a specific conversation, ordered by creation time.
     *
     * @param conversationId
     */
    @Override
    public List<ChatMessageResponse> getConversationMessages(Long conversationId) {
        return chatMessageRepository.findAllByConversationIdOrderByCreatedAtAsc(conversationId)
                .stream()
                .map(this::toChatMessageResponse)
                .toList();
    }

    /**
     * Retrieves all conversations for a specific customer, ordered by last updated time.
     *
     * @param customerId
     */
    @Override
    public List<ChatConversationResponse> getCustomerConversations(Long customerId) {
        return chatConversationRepository.findAllByCustomerIdOrderByUpdatedAtDesc(customerId)
                .stream()
                .map(this::toChatConversationResponse)
                .toList();
    }

    /**
     * Retrieves all conversations assigned to a specific admin, ordered by last updated time.
     *
     * @param adminId
     */
    @Override
    public List<ChatConversationResponse> getAdminConversations(Long adminId) {
        return chatConversationRepository
                .findAllByAssignedAdminIdOrderByUpdatedAtDesc(adminId)
                .stream()
                .map(this::toChatConversationResponse)
                .toList();
    }

    private ChatMessageResponse toChatMessageResponse(ChatMessage message){
        return ChatMessageResponse.builder()
                .id(message.getId())
                .conversationId(message.getConversation().getId())
                .senderType(toDtoSenderType(message.getSenderType()))
                .senderId(message.getSenderId())
                .content(message.getContent())
                .createdAt(message.getCreatedAt())
                .build();
    }

    private ChatConversationResponse toChatConversationResponse(ChatConversation conversation){
        return ChatConversationResponse.builder()
                .id(conversation.getId())
                .customerId(conversation.getCustomerId())
                .assignedAdminId(conversation.getAssignedAdminId())
                .status(conversation.getStatus().name())
                .createdAt(conversation.getCreatedAt())
                .updatedAt(conversation.getUpdatedAt())
                .build();
    }

    private SenderType toEntitySenderType(SenderTypeDto senderTypeDto){
        return SenderType.valueOf(senderTypeDto.name());
    }

    private SenderTypeDto toDtoSenderType(SenderType senderType){
        return SenderTypeDto.valueOf(senderType.name());
    }
}
