package com.food_delivery.DishPatch.services;

import com.food_delivery.DishPatch.models.Category;
import com.food_delivery.DishPatch.repositories.CategoryRepository;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public String addCategory(String name){
        Category newCategory = new Category();
        newCategory.setName(name);
        try {
            categoryRepository.save(newCategory);
            return "Category Created";
        } catch (Exception e) {
            return "Category Already Exists";
        }
    }
}
