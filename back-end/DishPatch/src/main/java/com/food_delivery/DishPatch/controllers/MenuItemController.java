package com.food_delivery.DishPatch.controllers;

import com.food_delivery.DishPatch.Response.ApiResponse;
import com.food_delivery.DishPatch.services.MenuItemService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("menu-item")
public class MenuItemController {
    private final MenuItemService menuItemService;

    public MenuItemController(MenuItemService menuItemService) {
        this.menuItemService = menuItemService;
    }

    @PostMapping("add/{item}")
    public ResponseEntity<ApiResponse> addMenuItem(@PathVariable String item){
        String response = menuItemService.addMenuItem(item);
        return ResponseEntity.ok(new ApiResponse(response));
    }
}
