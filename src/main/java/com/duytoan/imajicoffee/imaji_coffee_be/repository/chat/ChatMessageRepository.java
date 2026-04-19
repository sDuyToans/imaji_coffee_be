package com.duytoan.imajicoffee.imaji_coffee_be.repository.chat;

import com.duytoan.imajicoffee.imaji_coffee_be.entities.chat.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
    List<ChatMessage> findAllByConversationIdOrderByCreatedAtAsc(Long conversationId);
}
