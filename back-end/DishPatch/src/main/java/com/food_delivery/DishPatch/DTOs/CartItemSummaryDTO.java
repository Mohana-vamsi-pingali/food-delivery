package com.food_delivery.DishPatch.DTOs;

import com.food_delivery.DishPatch.models.RestaurantMenu;

import java.math.BigDecimal;
import java.util.List;

public class CartItemSummaryDTO {
    private Long id;
    private String name;
    private String imageUrl;
    private BigDecimal price;
    private int quantity;
    private BigDecimal lineTotal;
    private boolean isAvailable;

    public CartItemSummaryDTO(Long id, String name, String imageUrl, BigDecimal price, int quantity, BigDecimal lineTotal, boolean isAvailable) {
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
        this.price = price;
        this.quantity = quantity;
        this.lineTotal = lineTotal;
        this.isAvailable = isAvailable;
    }

    public CartItemSummaryDTO(Long id, String name, String imageUrl, BigDecimal price, int quantity, BigDecimal lineTotal) {
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
        this.price = price;
        this.quantity = quantity;
        this.lineTotal = lineTotal;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getLineTotal() {
        return lineTotal;
    }

    public void setLineTotal(BigDecimal lineTotal) {
        this.lineTotal = lineTotal;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setIsAvailable(boolean isAvailable) {
        this.isAvailable = isAvailable;
    }
}
