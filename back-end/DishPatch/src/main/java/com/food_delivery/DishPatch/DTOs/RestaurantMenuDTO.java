package com.food_delivery.DishPatch.DTOs;

import com.food_delivery.DishPatch.models.RestaurantMenu;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

public class RestaurantMenuDTO {
    private Long restaurantId;
    private String itemName;
    private String categoryName;
    private BigDecimal price;
    private String availability;
    private MultipartFile imageFile;

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    private String imageUrl;

    public RestaurantMenuDTO(String itemName, String categoryName, BigDecimal price, String availability, String imageUrl) {
        this.itemName = itemName;
        this.categoryName = categoryName;
        this.price = price;
        this.availability = availability;
        this.imageUrl = imageUrl;
    }

    public static RestaurantMenuDTO from(RestaurantMenu entity, String presignedUrl) {
        return new RestaurantMenuDTO(
                entity.getMenuitem().getName(),
                entity.getCategory().getName(),
                entity.getPrice(),
                entity.getAvailability().toString(),
                presignedUrl
        );
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public MultipartFile getImageFile() {
        return imageFile;
    }

    public void setImageFile(MultipartFile imageFile) {
        this.imageFile = imageFile;
    }

    public RestaurantMenuDTO() {
    }

    public Long getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(Long restaurantId) {
        this.restaurantId = restaurantId;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getAvailability() {
        return availability;
    }

    public void setAvailability(String availability) {
        this.availability = availability;
    }


}
