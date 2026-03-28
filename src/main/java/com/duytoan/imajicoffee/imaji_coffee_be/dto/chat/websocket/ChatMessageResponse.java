package com.duytoan.imajicoffee.imaji_coffee_be.dto.chat.websocket;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Data
@Builder
public class ChatMessageResponse {

    private String from;
    private String to;
    private String content;
    private Instant timestamp;

}
