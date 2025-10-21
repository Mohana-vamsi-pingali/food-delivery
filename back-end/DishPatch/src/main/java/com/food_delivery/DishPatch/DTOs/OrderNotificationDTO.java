package com.food_delivery.DishPatch.DTOs;

import com.food_delivery.DishPatch.models.Order;

import java.math.BigDecimal;

public class OrderNotificationDTO {
    private Long orderId;
    private Long restaurantId;
    private String restaurantName;
    private Long userId;
    private String userName;
    private BigDecimal totalPrice;

    public OrderNotificationDTO(Long orderId, Long restaurantId, String restaurantName, Long userId, String userName, BigDecimal totalPrice) {
        this.orderId = orderId;
        this.restaurantId = restaurantId;
        this.restaurantName = restaurantName;
        this.userId = userId;
        this.userName = userName;
        this.totalPrice = totalPrice;
    }

    public static OrderNotificationDTO from(Order order) {
        return new OrderNotificationDTO(
                order.getId(),
                order.getRestaurant().getId(),
                order.getRestaurant().getName(),
                order.getUser().getId(),
                order.getUser().getName(),
                order.getTotalPrice()
        );
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(Long restaurantId) {
        this.restaurantId = restaurantId;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }
}
