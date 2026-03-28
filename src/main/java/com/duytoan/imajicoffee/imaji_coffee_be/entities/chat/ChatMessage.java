package com.duytoan.imajicoffee.imaji_coffee_be.entities.chat;

import com.duytoan.imajicoffee.imaji_coffee_be.entities.BaseEntity;
import com.duytoan.imajicoffee.imaji_coffee_be.enums.MessageType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Chat Message Entity
 * @author duytoan
 * @since 01/13/2026
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChatMessage extends BaseEntity {
    private String content;
    private String sender;
    private MessageType messageType;
}
