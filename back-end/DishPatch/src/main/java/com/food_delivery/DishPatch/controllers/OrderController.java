package com.food_delivery.DishPatch.controllers;

import com.food_delivery.DishPatch.DTOs.CartSummaryDTO;
import com.food_delivery.DishPatch.Response.ApiResponse;
import com.food_delivery.DishPatch.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @PostMapping("/checkout")
    public ResponseEntity<CartSummaryDTO> checkOut(@RequestParam Long id){
        CartSummaryDTO responseDto = orderService.checkOut(id);
        return ResponseEntity.ok(responseDto);
    }

    @PostMapping("/place")
    public ResponseEntity<ApiResponse> placeOrder(@RequestParam Long id){
        String response = orderService.placeOrder(id);
        return ResponseEntity.ok(new ApiResponse((response)));
    }
}
