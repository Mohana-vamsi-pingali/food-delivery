package com.food_delivery.DishPatch.controllers;

import com.food_delivery.DishPatch.Response.ApiResponse;
import com.food_delivery.DishPatch.services.CategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("category")
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping("create/{name}")
    public ResponseEntity<ApiResponse> addCategory(@PathVariable String name){
        String msg = categoryService.addCategory(name);
        return ResponseEntity.ok().body(new ApiResponse(msg));
    }
}
