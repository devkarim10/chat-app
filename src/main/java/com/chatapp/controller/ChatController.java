package com.chatapp.controller;


import com.chatapp.model.ChatMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {

    @MessageMapping("/chat.connectChat")
    @SendTo("/topic/all")
    public ChatMessage connectChat(@Payload ChatMessage chatMessage , SimpMessageHeaderAccessor simpMessageHeaderAccessor){
        simpMessageHeaderAccessor.getSessionAttributes().put("username",chatMessage.getSender());

        return chatMessage;
    }


    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/all")
    public ChatMessage sendMessage(@Payload ChatMessage chatMessage){
        return chatMessage;
    }
}
