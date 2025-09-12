package com.food_delivery.DishPatch.repositories;

import com.food_delivery.DishPatch.models.RestaurantMenu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RestaurantMenuRepository extends JpaRepository<RestaurantMenu, Long> {
    public Optional<RestaurantMenu> findByMenuitemIdAndRestaurantId(Long menuItemId, Long restaurantId);
}
