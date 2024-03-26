package com.example.backendchat.Chat;

import java.time.LocalDateTime;
import java.util.UUID;

public class ChatMessage {
    private int id;
    private UUID userId;
    private String message;
    private LocalDateTime timeStamp;

    public ChatMessage(UUID userId, String message, LocalDateTime date){
        this.userId=userId;
        this.message=message;
        this.timeStamp=date;
    }

    public ChatMessage() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(LocalDateTime timeStamp) {
        this.timeStamp = timeStamp;
    }
}
