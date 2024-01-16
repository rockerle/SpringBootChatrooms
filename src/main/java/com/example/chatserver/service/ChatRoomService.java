package com.example.chatserver.service;

import com.example.chatserver.dto.ChatRoom;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Component
public class ChatRoomService {

    private final Logger logger = Logger.getLogger("CRS-LOGGER");
    private final List<ChatRoom> chatRoomList;
    private final List<String> people;

    public ChatRoomService(){
        this.chatRoomList = new ArrayList<>();
        this.people = new ArrayList<>();
        this.createRoom("general");
    }
    public ChatRoom createRoom(String name){
        this.logger.info("Creating ChatRoom with name: "+name);
        ChatRoom c = new ChatRoom(name);
        chatRoomList.add(c);
        return c;
    }

    public void joinRoom(String name){
        this.people.add(name);
    }
    public void leaveRoom(String name){
        this.people.remove(name);
    }

    public ChatRoom getChatRoom(String name){
        for(ChatRoom cr:chatRoomList){
            if(cr.getName().equals(name))
                return cr;
        };
        this.logger.info("no ChatRoom found with name "+name);
        return null;
    }
    public List<ChatRoom> getChatRoomList(){
        return this.chatRoomList;
    }
}
