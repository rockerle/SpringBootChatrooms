package com.example.chatserver.dto;

public class ChatMessage {
    private String content;
    private String sender;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String toString(){
        return "["+this.sender+"]: "+this.content;
    }
}
