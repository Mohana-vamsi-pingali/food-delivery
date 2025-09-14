package com.food_delivery.DishPatch.controllers;

import com.amazonaws.Response;
import com.food_delivery.DishPatch.DTOs.RestaurantMenuDTO;
import com.food_delivery.DishPatch.Response.ApiResponse;
import com.food_delivery.DishPatch.security.AuthUtils;
import com.food_delivery.DishPatch.services.RestaurantMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("restaurant-menu")
public class RestaurantMenuController {
    @Autowired
    private RestaurantMenuService restaurantMenuService;
    @Autowired
    private final AuthUtils authUtils = new AuthUtils();
    @PostMapping("add")
    public ResponseEntity<ApiResponse> addMenuItem(RestaurantMenuDTO dto){
        if(authUtils.hasRole("RESTAURANT_ADMIN")) {
            String msg = restaurantMenuService.addMenuItem(dto);
            return ResponseEntity.ok().body(new ApiResponse(msg));
        }
        return ResponseEntity.badRequest().body(new ApiResponse("Unauthorized"));
    }

    @GetMapping("{restaurantId}/items/{menuItemId}")
    public ResponseEntity<RestaurantMenuDTO> getMenuItem(@PathVariable Long restaurantId, @PathVariable Long menuItemId){
        RestaurantMenuDTO dto = restaurantMenuService.getMenuItem(restaurantId, menuItemId);
        return ResponseEntity.ok(dto);
    }

    @GetMapping("{restaurantId}/categories/{categoryId}/items")
    public ResponseEntity<List<RestaurantMenuDTO>> getAllItemsByCategory(@PathVariable Long restaurantId, @PathVariable Long categoryId){
        return ResponseEntity.ok().body(restaurantMenuService.getAllMenuItemsByCategory(restaurantId, categoryId));
    }

}
