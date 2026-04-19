package com.duytoan.imajicoffee.imaji_coffee_be.entities.chat;

import com.duytoan.imajicoffee.imaji_coffee_be.entities.BaseEntity;
import com.duytoan.imajicoffee.imaji_coffee_be.enums.SenderType;
import jakarta.persistence.*;
import lombok.*;

/**
 * Chat Message Entity
 * @author duytoan
 * @since 01/13/2026
 */
@Entity
@Table(name = "chat_message")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChatMessage extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)

    @JoinColumn(name = "conversation_id")

    private ChatConversation conversation;

    @Enumerated(EnumType.STRING)
    private SenderType senderType;

    private Long senderId;

    private String senderName;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;
}
