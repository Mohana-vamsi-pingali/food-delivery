package com.food_delivery.DishPatch.services;

import com.food_delivery.DishPatch.DTOs.RestaurantDTO;
import com.food_delivery.DishPatch.models.Restaurant;
import com.food_delivery.DishPatch.models.User;
import com.food_delivery.DishPatch.repositories.RestaurantRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RestaurantService {
    private final RestaurantRepository restaurantRepository;

    public RestaurantService(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    public Long getOwnerId(Long restaurantId){
        User u =  restaurantRepository.findOwnerById(restaurantId);
        return u.getId();
    }

    public Restaurant getRestaurant(Long id){
        return restaurantRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Cannot FInd Restaurant"));
    }

    public void createRestaurant(RestaurantDTO restaurantDetails){
        Restaurant restaurant = new Restaurant();
        restaurant.setName(restaurantDetails.getName());
        restaurant.setAddress(restaurantDetails.getAddress());
        restaurant.setOwner(restaurantDetails.getOwner());

        restaurantRepository.save(restaurant);
    }
}
