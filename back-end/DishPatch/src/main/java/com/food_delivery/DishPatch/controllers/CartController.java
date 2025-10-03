package com.food_delivery.DishPatch.controllers;

import com.food_delivery.DishPatch.DTOs.AddCartItemDTO;
import com.food_delivery.DishPatch.Response.ApiResponse;
import com.food_delivery.DishPatch.services.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
public class CartController {
    @Autowired
    public CartService cartService;
    @PostMapping("/items")
    public ResponseEntity<AddCartItemDTO> addItem(@RequestBody AddCartItemDTO dto){
        AddCartItemDTO responseDTO = cartService.addCartItem(dto);
        return ResponseEntity.ok(responseDTO);
    }

    @PostMapping("/items/update")
    public ResponseEntity<ApiResponse> changeQuantity(@RequestParam Long id, @RequestParam boolean increase){
        String msg = cartService.changeQuantity(id, increase);
        return ResponseEntity.ok(new ApiResponse(msg));
    }
}
