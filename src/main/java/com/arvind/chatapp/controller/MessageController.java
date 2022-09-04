package com.arvind.chatapp.controller;

import com.arvind.chatapp.entity.Message;
import com.arvind.chatapp.entity.User;
import com.arvind.chatapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class MessageController {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private UserService userService;

    @MessageMapping("/chat/{to}")
    public void sendMessage(@DestinationVariable String to, Message message){
        User userTo = userService.findByName(to);
        User userFrom = userService.findByName(message.getFrom());
        if(userFrom == null || userTo == null){
            ResponseEntity.status(404).body("Users not found");
        }
        //messagingTemplate.convertAndSend("/topic/messages/" + to, message);
        messagingTemplate.convertAndSend("/topic/messages." + to, message);
        //return ResponseEntity.ok().body("Success");
    }
}
