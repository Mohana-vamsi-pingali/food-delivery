package com.food_delivery.DishPatch.repositories;

import com.food_delivery.DishPatch.models.MenuItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MenuItemRepository extends JpaRepository<MenuItem, Long> {
    public Optional<MenuItem> findByName(String name);
}
