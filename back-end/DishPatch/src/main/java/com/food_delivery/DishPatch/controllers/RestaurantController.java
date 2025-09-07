package com.food_delivery.DishPatch.controllers;

import com.food_delivery.DishPatch.DTOs.RestaurantDTO;
import com.food_delivery.DishPatch.Response.ApiResponse;
import com.food_delivery.DishPatch.services.RestaurantService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("restaurant")
public class RestaurantController {
    private final RestaurantService restaurantService;

    public RestaurantController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @PostMapping("create")
    public ResponseEntity<ApiResponse> createRestaurant(@Valid @RequestBody RestaurantDTO restaurantDTO){
        restaurantService.createRestaurant(restaurantDTO);
        return ResponseEntity.ok().body(new ApiResponse());
    }
}
