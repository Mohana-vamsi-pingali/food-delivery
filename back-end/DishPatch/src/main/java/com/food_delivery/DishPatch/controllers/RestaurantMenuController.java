package com.food_delivery.DishPatch.controllers;

import com.amazonaws.Response;
import com.food_delivery.DishPatch.DTOs.RestaurantMenuDTO;
import com.food_delivery.DishPatch.Response.ApiResponse;
import com.food_delivery.DishPatch.services.RestaurantMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("restaurant-menu")
public class RestaurantMenuController {
    @Autowired
    private RestaurantMenuService restaurantMenuService;
    @PostMapping("add")
    public ResponseEntity<ApiResponse> addMenuItem(RestaurantMenuDTO dto){
        String msg = restaurantMenuService.addMenuItem(dto);
        return ResponseEntity.ok().body(new ApiResponse(msg));
    }
}
