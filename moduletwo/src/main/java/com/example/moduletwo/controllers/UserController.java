package com.example.moduletwo.controllers;


import com.example.moduletwo.models.UserEntity;
import com.example.moduletwo.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;

@RestController
@RequestMapping("/api/users/")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("{userId}")
    public ResponseEntity<Object> getUser(@PathVariable Long userId) throws SQLException {
        return new ResponseEntity<>(userService.getUser(userId), HttpStatus.OK);
    }
}
