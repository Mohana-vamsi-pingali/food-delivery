package com.food_delivery.DishPatch.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestSocketController {
    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @GetMapping("/test-socket")
    public String testSocket() {
        messagingTemplate.convertAndSend("/topic/orders/1",
                "{\"message\":\"Order received successfully!\"}");
        return "Sent!";
    }
}

