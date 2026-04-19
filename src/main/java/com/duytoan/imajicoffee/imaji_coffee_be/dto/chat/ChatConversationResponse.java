package com.duytoan.imajicoffee.imaji_coffee_be.dto.chat;

import lombok.*;

import java.time.Instant;

/**
 * DTO for {@link com.duytoan.imajicoffee.imaji_coffee_be.entities.chat.ChatConversation}
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ChatConversationResponse{

    private Long id;
    private Long customerId;
    private Long assignedAdminId;
    private String status;
    private Instant createdAt;
    private Instant updatedAt;
}
