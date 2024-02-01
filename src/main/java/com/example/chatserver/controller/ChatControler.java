package com.example.chatserver.controller;

import com.example.chatserver.dto.ChatMessage;
import com.example.chatserver.dto.ChatRoom;
import com.example.chatserver.service.ChatRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.logging.Logger;

@Controller
public class ChatControler {

    private final Logger logger = Logger.getLogger("ChatController");
    private final ChatRoomService crs;

    @Autowired
    public ChatControler(ChatRoomService crs){
        this.crs = crs;
    }

    @MessageMapping("/messageIn/{name}")
    @SendTo("/broadcast/{name}")
    public ChatMessage send(@DestinationVariable String name, Authentication auth, String message) {
        this.logger.info("received message from ChatRoom[" + name + "] by user "+auth.getName());
        ChatRoom cr = this.crs.getChatRoom(name);
        ChatMessage msg = new ChatMessage();
        msg.setSender(auth.getName());
        msg.setContent(message);
        cr.getMessageHistory().add(msg);
        return msg;
    }

    @SubscribeMapping("/broadcast/{name}")
    @SendTo(value="/broadcast/{name}")
    public ChatMessage subscribed(Authentication auth){
        this.logger.info("new subscriber : "+auth.getName());
        ChatMessage cm = new ChatMessage();
        cm.setSender(auth.getName());
        cm.setContent("Joined the room");
        return cm;
    }

    @RequestMapping(value = "/chat/{room}", method = RequestMethod.GET)
    public String chatting(@PathVariable String room, Model model, Authentication authentication){
        this.logger.info("Called /chat/{name} with name="+room+" to join");
        ChatRoom chatroom = crs.getChatRoom(room);
        model.addAttribute("chatroom", chatroom);
        return "chatRoom";
    }
}
