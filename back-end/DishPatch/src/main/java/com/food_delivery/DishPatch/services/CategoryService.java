package com.food_delivery.DishPatch.services;

import com.food_delivery.DishPatch.models.Category;
import com.food_delivery.DishPatch.repositories.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Optional<Category> getCategory(String name){
        return Optional.of(categoryRepository.findByName(name).orElseThrow(()-> new IllegalArgumentException("Cannot Find Category")));
    }

    public Category addCategoryInternal(String name){
        Category newCategory = new Category();
        newCategory.setName(name);
        try {
            return categoryRepository.save(newCategory);
        } catch (Exception e) {
            throw new IllegalArgumentException("Cannot Create a new Category");
        }
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
