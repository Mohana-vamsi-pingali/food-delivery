package com.food_delivery.DishPatch.models;

import jakarta.persistence.*;

@Entity
@Table(name = "drivers")
public class Driver {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public static enum DriverAvailability {OFFLINE, ONLINE};

    @Enumerated(EnumType.STRING)
    @Column(name = "driver_availability", nullable = false)
    private DriverAvailability driverAvailability = DriverAvailability.OFFLINE;

    public Driver() {

    }

    public Driver(Long id, User user, DriverAvailability driverAvailability) {
        this.id = id;
        this.user = user;
        this.driverAvailability = driverAvailability;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public DriverAvailability getDriverAvailability() {
        return driverAvailability;
    }

    public void setDriverAvailability(DriverAvailability driverAvailability) {
        this.driverAvailability = driverAvailability;
    }
}
