package com.food_delivery.DishPatch.models;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "restaurant_menu")
public class RestaurantMenu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "restaurant_id", nullable = false)
    private Restaurant restaurant;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "menu_id", nullable = false)
    private MenuItem menuitem;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="category_id", nullable = false)
    private Category category;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    public static enum Availability {AVAILABLE, OUT_OF_STOCK};

    @Enumerated(EnumType.STRING)
    @Column(name = "availability", nullable = false)
    private Availability availability = Availability.AVAILABLE;

    @Column(name = "image_url", columnDefinition = "TEXT")
    private String imageUrl;

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public RestaurantMenu() {
    }

    public RestaurantMenu(Long id, Restaurant restaurant, MenuItem menuitem, Category category, BigDecimal price, Availability availability, String imageUrl) {
        this.id = id;
        this.restaurant = restaurant;
        this.menuitem = menuitem;
        this.category = category;
        this.price = price;
        this.availability = availability;
        this.imageUrl = imageUrl;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public MenuItem getMenuitem() {
        return menuitem;
    }

    public void setMenuitem(MenuItem menuitem) {
        this.menuitem = menuitem;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Availability getAvailability() {
        return availability;
    }

    public void setAvailability(Availability availability) {
        this.availability = availability;
    }
}
