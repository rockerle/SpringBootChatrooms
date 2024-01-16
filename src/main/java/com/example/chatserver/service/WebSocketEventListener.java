package com.example.chatserver.service;

import com.example.chatserver.dto.ChatMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.util.logging.Logger;

//@Component
public class WebSocketEventListener {
    private final Logger logger = Logger.getLogger("WSEL");

    private final SimpMessageSendingOperations messageTemplate;

//    @Autowired
    public WebSocketEventListener(SimpMessageSendingOperations s){
        this.messageTemplate=s;
    }

//    @EventListener
    public void handleWebSocketConnectListener(SessionConnectedEvent evt){
        this.logger.info("Connected "+evt.getUser().getName());
    }

//    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent evt){
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(evt.getMessage());

        String user = (String) headerAccessor.getSessionAttributes().get("username");
        if(user!=null) {
            this.logger.info("disconnecting user "+user);
            ChatMessage msg = new ChatMessage();
            msg.setSender(user);
            messageTemplate.convertAndSend("/chat/{name}",msg);
        }
    }
}
