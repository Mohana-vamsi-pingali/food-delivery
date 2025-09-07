package com.food_delivery.DishPatch.DTOs;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public class ApprovalRequestsDTO {
    @NotNull(message = "Name Should not be null")
    private String name;

    @NotNull(message = "Name Should not be null")
    @Email(message = "Invalid Email Format")
    private String email;

    private String password;

    @NotNull(message = "Role Should not be null")
    private String role;

    @NotNull(message = "Address cannot be null")
    private String address;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
