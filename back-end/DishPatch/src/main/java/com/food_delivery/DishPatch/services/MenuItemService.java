package com.food_delivery.DishPatch.services;

import com.food_delivery.DishPatch.models.MenuItem;
import com.food_delivery.DishPatch.repositories.MenuItemRepository;
import org.springframework.stereotype.Service;

@Service
public class MenuItemService {
    private final MenuItemRepository menuItemRepository;

    public MenuItemService(MenuItemRepository menuItemRepository) {
        this.menuItemRepository = menuItemRepository;
    }

    public String addMenuItem(String item){
        MenuItem menuItem = new MenuItem();
        menuItem.setName(item);

        try {
            menuItemRepository.save(menuItem);
        } catch (Exception e) {
            throw new IllegalArgumentException(e.getMessage());
        }
        return "Successfully added Menu-item";
    }
}
