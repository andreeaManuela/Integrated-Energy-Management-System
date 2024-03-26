package com.example.backendchat.Chat;

public class SeenMessageNotification {
    private String userId;
    private String seen;

    public SeenMessageNotification(String userId, String seen){
        this.userId=userId;
        this.seen=seen;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getSeen() {
        return seen;
    }

    public void setSeen(String seen) {
        this.seen = seen;
    }
}
