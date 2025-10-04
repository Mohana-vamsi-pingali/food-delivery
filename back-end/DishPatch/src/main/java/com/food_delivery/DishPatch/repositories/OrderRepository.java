package com.food_delivery.DishPatch.repositories;

import com.food_delivery.DishPatch.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
}
