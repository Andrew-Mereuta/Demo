package com.example.moduleone.controllers;

import com.example.moduleone.filters.CreateJWT;
import com.example.moduleone.filters.JWTInfo;
import com.example.moduleone.models.UserEntity;
import com.example.moduleone.models.UserRequest;
import com.example.moduleone.services.UserService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;


    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<UserEntity> getAllUsers(){
        return userService.getAllUsers();
    }

    @PostMapping
    public void createNewUser(@RequestBody UserRequest user){
        userService.saveUser(user);
    }

    @PostMapping("/ta")
    public void createNewEnhancedUser(@RequestBody UserRequest user) {
        userService.saveUserAndTA(user);
    }



}
