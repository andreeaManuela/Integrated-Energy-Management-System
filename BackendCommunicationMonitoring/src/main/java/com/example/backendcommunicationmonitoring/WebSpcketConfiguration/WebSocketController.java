package com.example.backendcommunicationmonitoring.WebSpcketConfiguration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebSocketController {

private final SimpMessagingTemplate messagingTemplate;

    @Autowired
    public WebSocketController(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    // Endpoint pentru a iniția trimiterea unui mesaj către /topic/client
    @GetMapping("/trigger-message")
    public String triggerMessage() {
        String message = "HELLO WEBSOCKET! I MADE IT!!";
        messagingTemplate.convertAndSend("/topic/notifications", message);
        return "Message sent!";
    }
}
