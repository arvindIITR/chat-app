package com.arvind.chatapp.controller;

import com.arvind.chatapp.entity.User;
import com.arvind.chatapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;

@Controller
@CrossOrigin
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String index(){
        return "home";
    }

    @GetMapping("/register")
    public String register(){
        return "register";
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @PostMapping("/add/user")
    public ResponseEntity addUser(@RequestBody User user){
        User userExist = userService.findByEmail(user.getEmail());
        if(userExist != null){
            return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body("User already exist");
        }
        userService.addUser(user);
        return ResponseEntity.status(200).body("success");
    }

    @PostMapping("/authenticate")
    public RedirectView authenticate(String username, String password, RedirectAttributes redirectAttributes){
        User userDetails = userService.findByEmail(username);
        RedirectView redirectView = new RedirectView();
        if(userDetails == null){
            //redirectView.setContextRelative(true);
            redirectView.setUrl("login");
            return redirectView;
        }
        if(!userDetails.getPassword().equals(password)){
            //redirectView.setContextRelative(true);
            redirectView.setUrl("login");
            return redirectView;
        }
        //redirectView.setContextRelative(true);
        redirectView.setUrl("chat");
        redirectAttributes.addFlashAttribute("alert", userDetails.getName());
        return redirectView;
    }

    @GetMapping("/chat")
    public String communicate(){
        return "chat";
    }

}
