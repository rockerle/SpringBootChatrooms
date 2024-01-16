package com.example.chatserver.controller;

import com.example.chatserver.dto.ChatRoom;
import com.example.chatserver.service.ChatRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.logging.Logger;

@Controller
public class ChatRoomController {

    private final Logger logger = Logger.getLogger("CRC-LOGGER");
    private final ChatRoomService crs;

    @Autowired
    public ChatRoomController(ChatRoomService crs){
        this.crs=crs;
    }

    @GetMapping(value="/chat")
    public String allChatRooms(Model model){
        this.logger.info("adding "+crs.getChatRoomList().size()+" chatrooms to the model");
        model.addAttribute("rooms",crs.getChatRoomList());
        model.addAttribute("newRoomName", "<name>");
        model.addAttribute("join",new ChatRoom());
        return "roomList";
    }

    @RequestMapping("/joinroom")
    public String joinRoom(@ModelAttribute("join") ChatRoom name, Authentication auth){
        this.logger.info(auth.getName()+" joining "+name.getName());
        return "redirect:/chat/"+name.getName();
    }
    @RequestMapping("/createroom")
    public String createRoom(@ModelAttribute("newRoomName") String name, Authentication auth, Model model){
        this.logger.info("Creating new room: "+name);
        ChatRoom newRoom = crs.createRoom(name);
        //newRoom.join();
        String uri = "redirect:/chat/"+name;
        return uri;
    }
}
