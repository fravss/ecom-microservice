package com.ecom.app.controller;

import com.ecom.app.dto.UserResponse;
import com.ecom.app.model.User;
import com.ecom.app.service.UserService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        return  new ResponseEntity<>(userService.getAllUsers(),
                HttpStatus.OK);
    }

//    @PostMapping
//    public ResponseEntity<String> saveUser(@RequestBody User user) {
//        userService.addUser(user);
//        return ResponseEntity.ok("User added successfully");
//    }
}
