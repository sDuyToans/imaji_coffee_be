//package com.duytoan.imajicoffee.imaji_coffee_be.config.ws;
//
//import com.duytoan.imajicoffee.imaji_coffee_be.entities.chat.ChatMessage;
//import com.duytoan.imajicoffee.imaji_coffee_be.enums.MessageType;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.context.event.EventListener;
//import org.springframework.messaging.simp.SimpMessagingTemplate;
//import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
//import org.springframework.stereotype.Component;
//import org.springframework.web.socket.messaging.SessionDisconnectEvent;
//
///**
// * Web Socket Event Listener
// * @author duytoan
// * @since 01/13/2026
// */
//@Component
//@Slf4j
//@RequiredArgsConstructor
//public class WebSocketEventListener {
//
//    private final SimpMessagingTemplate brokerMessagingTemplate;
//
//    /**
//     * Handles WebSocket disconnect events by retrieving the username from the session attributes,
//     * logging the disconnection, and broadcasting a LEAVE message to the "/topic/public" destination.
//     * @param event -> SessionDisconnectEvent
//     */
//    @EventListener
//    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
//        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
//        if (headerAccessor.getSessionAttributes() == null) {
//            return; // nothing to do
//        }
//        String username = (String) headerAccessor.getSessionAttributes().get("username");
//
//        if (username != null) {
//            log.info("User Disconnected : {}", username);
//            ChatMessage chatMessage = ChatMessage.builder()
//                    .messageType(MessageType.LEAVE)
//                    .sender(username)
//                    .content(event.getMessage().toString())
//                    .build();
//            log.info(chatMessage.toString());
//            brokerMessagingTemplate.convertAndSend("/topic/public", chatMessage);
//        }
//    }
//}
