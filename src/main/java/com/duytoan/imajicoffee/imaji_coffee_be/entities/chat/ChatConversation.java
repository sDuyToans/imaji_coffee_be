package com.duytoan.imajicoffee.imaji_coffee_be.entities.chat;

import com.duytoan.imajicoffee.imaji_coffee_be.entities.BaseEntity;
import com.duytoan.imajicoffee.imaji_coffee_be.enums.ConversationStatus;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

/**
 * ChatConversation Entity
 * @author duytoan
 * @since Apr 18 2026
 */
@Entity
@Table(name = "chat_conversation")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatConversation extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long customerId;

    private Long assignedAdminId;

    @Enumerated(EnumType.STRING)
    private ConversationStatus status;

    @OneToMany(mappedBy = "conversation", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ChatMessage> messages = new ArrayList<>();
}
