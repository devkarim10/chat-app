package com.chatapp.controller;

import com.chatapp.model.ChatMessage;
import com.chatapp.model.ChatType;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.awt.*;


@Component
public class WebSocketEventListener {

    private static final Logger LOGGER = (Logger) LoggerFactory.getLogger(WebSocketEventListener.class);

    @Autowired
    private SimpMessageSendingOperations operations;

    @EventListener
    public void handelWebSocketConnectListener(final SessionConnectedEvent sessionConnectedEvent){

        LOGGER.info("Connection Established.");
    }

    public void handelWebSocketDisconnectListener(final SessionDisconnectEvent disconnectEvent){
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(disconnectEvent.getMessage());

        String username = (String) headerAccessor.getSessionAttributes().get("username");

        final ChatMessage chatMessage = ChatMessage.builder().chatType(ChatType.DISCONNECT).sender(username).build();

        operations.convertAndSend("/topic/public",chatMessage);

    }
}
