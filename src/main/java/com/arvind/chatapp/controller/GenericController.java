package com.arvind.chatapp.controller;

import com.arvind.chatapp.entity.Message;
import com.arvind.chatapp.entity.User;
import com.arvind.chatapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin
public class GenericController {

    @Autowired
    private UserService userService;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @GetMapping("/fetchAllUsers")
    public List<String> getAllUsers(@RequestParam(name="excludedUser") String excludedUser){
        List<User> users = userService.getAllUsers(excludedUser);
        List<String> userNames = new ArrayList<>();
        for(User user: users){
            userNames.add(user.getName());
        }
        return userNames;
    }

    @PostMapping("/send")
    public ResponseEntity sendMessage(@RequestBody Message message){
        String from = message.getFrom();
        String to = message.getTo();
        User userFrom = userService.findByName(from);
        User userTo = userService.findByName(to);
        if(userFrom == null || userTo == null)
            return ResponseEntity.badRequest().body("User not exist" + userFrom !=null ? userFrom : userTo);
        messagingTemplate.convertAndSend("/topic/messages." +message.getTo(), message);
        return ResponseEntity.ok().body("Message sent successfully");
    }
}
