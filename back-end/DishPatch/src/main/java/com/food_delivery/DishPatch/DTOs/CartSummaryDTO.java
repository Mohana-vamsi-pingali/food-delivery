package com.food_delivery.DishPatch.DTOs;

import java.util.List;
import java.math.BigDecimal;

public class CartSummaryDTO {
    private Long cartId;
    private String restaurantName;
    private List<CartItemSummaryDTO> items;
    private BigDecimal subtotal;
    private BigDecimal deliveryFee;
    private BigDecimal tax;
    private BigDecimal total;

    public CartSummaryDTO(Long cartId, String restaurantName, List<CartItemSummaryDTO> items, BigDecimal subtotal, BigDecimal deliveryFee, BigDecimal tax, BigDecimal total) {
        this.cartId = cartId;
        this.restaurantName = restaurantName;
        this.items = items;
        this.subtotal = subtotal;
        this.deliveryFee = deliveryFee;
        this.tax = tax;
        this.total = total;
    }

    public Long getCartId() {
        return cartId;
    }

    public void setCartId(Long cartId) {
        this.cartId = cartId;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }

    public BigDecimal getDeliveryFee() {
        return deliveryFee;
    }

    public void setDeliveryFee(BigDecimal deliveryFee) {
        this.deliveryFee = deliveryFee;
    }

    public BigDecimal getTax() {
        return tax;
    }

    public void setTax(BigDecimal tax) {
        this.tax = tax;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public List<CartItemSummaryDTO> getItems() {
        return items;
    }

    public void setItems(List<CartItemSummaryDTO> items) {
        this.items = items;
    }
}
