package com.duytoan.imajicoffee.imaji_coffee_be.dto.chat;

import com.duytoan.imajicoffee.imaji_coffee_be.enums.SenderTypeDto;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatMessageRequest {

    private String content;
    private String senderName;
    private SenderTypeDto senderType;
    private Long senderId;

}
