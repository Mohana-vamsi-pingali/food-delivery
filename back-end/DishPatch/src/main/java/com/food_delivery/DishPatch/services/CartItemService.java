package com.food_delivery.DishPatch.services;

import com.food_delivery.DishPatch.repositories.CartItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartItemService {
    @Autowired
    public CartItemRepository cartItemRepository;

}
