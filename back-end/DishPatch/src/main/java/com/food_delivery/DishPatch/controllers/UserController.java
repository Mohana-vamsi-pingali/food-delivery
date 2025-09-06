package com.food_delivery.DishPatch.controllers;

import com.food_delivery.DishPatch.DTOs.UserDTO;
import com.food_delivery.DishPatch.DTOs.UserLoginDTO;
import com.food_delivery.DishPatch.DTOs.UserResponseDTO;
import com.food_delivery.DishPatch.models.User;
import com.food_delivery.DishPatch.services.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("create")
    public ResponseEntity<UserResponseDTO> createUser(@Valid @RequestBody UserDTO user){
        UserResponseDTO response = userService.createUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("login")
    public ResponseEntity<Map<String, String>> loginUser(@RequestBody UserLoginDTO user){
        String response = userService.loginUser(user);
        Map<String, String> body = new HashMap<>();
        body.put("role", response);
        return ResponseEntity.ok().body(body);
    }
}
