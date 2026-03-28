package com.duytoan.imajicoffee.imaji_coffee_be.dto.chat.websocket;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ChatMessageRequest {

    @NotBlank
    private String content;

    private String to;
}
