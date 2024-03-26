package com.example.backendchat.Chat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {
    private final SimpMessagingTemplate messagingTemplate;

    @Autowired
    public ChatController(SimpMessagingTemplate simpMessagingTemplate){
        this.messagingTemplate=simpMessagingTemplate;
    }

    //client -> admin
    @MessageMapping("/chat.clientToAdmin")
    public void handleClientMessage(ChatMessage message) {
        ChatMessage chatMessage = new ChatMessage(
                message.getUserId(),
                message.getMessage(),
                message.getTimeStamp()
        );

        System.out.println("Mesaj trimis de la CLIENT spre ADMIN:  " + chatMessage.getMessage());
        messagingTemplate.convertAndSend("/topic/adminMessages", message);
    }

    //admin->client
    @MessageMapping("/chat.adminToClient")
    public void handleAdminMessage(ChatMessage message){
        System.out.println("Mesaj trimis de la ADMIN spre CLIENT: "+ message.getMessage());
        messagingTemplate.convertAndSend("/topic/client/"+ message.getUserId(), message);
    }

    //clientul marcheaza mesajul ca citit
    @MessageMapping("/seen-from-client")
    public void messageReadAsClientToAdmin(SeenMessageNotification seen){
        messagingTemplate.convertAndSend("/topic/seen-as-client/" + seen.getUserId(), seen);
        System.out.println("ADMIN, userul a citit mesajul! " + seen.getUserId());
    }

    //adminul marcheaza mesajul ca citit
    @MessageMapping("/seen-from-admin")
    public void messageReadAsAdminToClient(SeenMessageNotification seen){
        messagingTemplate.convertAndSend("/topic/seen-as-admin/"+ seen.getUserId(), seen);
        System.out.println("USER, admin a citit mesajul! " + seen.getUserId());
    }


}
