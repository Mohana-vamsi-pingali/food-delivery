package com.food_delivery.DishPatch.Exceptions;

public class MenuItemAlreadyPresentException extends RuntimeException{
    public MenuItemAlreadyPresentException(String message) {
        super(message);
    }
}
