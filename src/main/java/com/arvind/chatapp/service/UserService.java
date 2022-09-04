package com.arvind.chatapp.service;

import com.arvind.chatapp.entity.User;
import com.arvind.chatapp.repository.UserRepository;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@NoArgsConstructor
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public ResponseEntity addUser(User user){
        userRepository.save(user);
        return ResponseEntity.ok().body("Success");
    }

    public User findByEmail(String email){
        return userRepository.findByEmail(email);
    }

    public User findByName(String name){
        return userRepository.findByName(name);
    }

    public List<User> getAllUsers(String excludedUser){
        return userRepository.findFriendsListFor(excludedUser);
    }
}
