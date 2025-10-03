package com.food_delivery.DishPatch.repositories;

import com.food_delivery.DishPatch.models.Cart;
import com.food_delivery.DishPatch.models.Cart.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    Optional<Cart> findByUserIdAndStatus(Long id, Status status);
}
