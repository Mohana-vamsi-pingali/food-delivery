package com.food_delivery.DishPatch.DTOs;

import com.food_delivery.DishPatch.models.User;

public class RestaurantDTO {
    private String name;
    private User owner;
    private String address;

    public RestaurantDTO() {
    }

    public RestaurantDTO(String name, User owner, String address) {
        this.name = name;
        this.owner = owner;
        this.address = address;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
