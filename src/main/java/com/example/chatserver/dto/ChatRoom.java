package com.example.chatserver.dto;

import java.util.ArrayList;
import java.util.List;

public class ChatRoom {

    private String name;
    private List<String> people = new ArrayList<>();
    private List<ChatMessage> messageHistory = new ArrayList<>();

    public ChatRoom(){}
    public ChatRoom(String name){this.name=name;}
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public List<ChatMessage> getMessageHistory() {
        return messageHistory;
    }

    public void setMessageHistory(List<ChatMessage> messageHistory) {
        this.messageHistory = messageHistory;
    }
}
