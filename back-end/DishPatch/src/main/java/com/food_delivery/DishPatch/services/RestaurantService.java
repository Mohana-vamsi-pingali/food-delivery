package com.food_delivery.DishPatch.services;

import com.food_delivery.DishPatch.DTOs.RestaurantDTO;
import com.food_delivery.DishPatch.models.Restaurant;
import com.food_delivery.DishPatch.repositories.RestaurantRepository;
import org.springframework.stereotype.Service;

@Service
public class RestaurantService {
    private final RestaurantRepository restaurantRepository;

    public RestaurantService(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    public void createRestaurant(RestaurantDTO restaurantDetails){
        Restaurant restaurant = new Restaurant();
        restaurant.setName(restaurantDetails.getName());
        restaurant.setAddress(restaurantDetails.getAddress());
        restaurant.setOwner(restaurantDetails.getOwner());

        restaurantRepository.save(restaurant);
    }
}
