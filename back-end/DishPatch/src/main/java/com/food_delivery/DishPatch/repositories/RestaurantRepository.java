package com.food_delivery.DishPatch.repositories;

import com.amazonaws.services.s3.model.Owner;
import com.food_delivery.DishPatch.models.Restaurant;
import com.food_delivery.DishPatch.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
    @Query("SELECT r.owner FROM Restaurant r WHERE r.id=:restaurantId")
    User findOwnerById(Long restaurantId);
}
