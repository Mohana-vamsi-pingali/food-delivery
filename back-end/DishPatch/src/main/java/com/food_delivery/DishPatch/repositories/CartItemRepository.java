package com.food_delivery.DishPatch.repositories;

import com.food_delivery.DishPatch.models.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    void deleteByCartId(Long cartId);
    List<CartItem> findByCartId(Long cartId);
}
