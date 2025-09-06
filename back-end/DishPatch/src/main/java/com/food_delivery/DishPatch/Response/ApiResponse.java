package com.food_delivery.DishPatch.Response;

public class ApiResponse {
    private String message;

    public ApiResponse() {
    }

    public ApiResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
