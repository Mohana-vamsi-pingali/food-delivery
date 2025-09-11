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

    public MenuItem getMenuItem(String name){
        return menuItemRepository.findByName(name).orElseThrow(()->new IllegalArgumentException("Cannot Find Menu Item"));
    }

    public MenuItem addMenuItemInternal(String item){
        MenuItem menuItem = new MenuItem();
        menuItem.setName(item);

        try {
            return menuItemRepository.save(menuItem);
        } catch (Exception e) {
            throw new IllegalArgumentException(e.getMessage());
        }
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
