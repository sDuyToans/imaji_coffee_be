package com.duytoan.imajicoffee.imaji_coffee_be.repository.chat;

import com.duytoan.imajicoffee.imaji_coffee_be.entities.chat.ChatConversation;
import com.duytoan.imajicoffee.imaji_coffee_be.enums.ConversationStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ChatConversationRepository extends JpaRepository<ChatConversation, Long> {
    Optional<ChatConversation> findByCustomerIdAndStatusInOrderByUpdatedAtDesc(
            Long customerId,
            List<ConversationStatus> statuses
    );
    List<ChatConversation> findAllByAssignedAdminIdOrderByUpdatedAtDesc(Long assignedAdminId);
    List<ChatConversation> findAllByCustomerIdOrderByUpdatedAtDesc(Long customerId);
}
