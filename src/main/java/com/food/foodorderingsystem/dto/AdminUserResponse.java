package com.food.foodorderingsystem.dto;

import com.food.foodorderingsystem.entity.Role;

import java.time.LocalDateTime;

public class AdminUserResponse {

    private Long id;
    private String email;
    private String fullName;
    private String phoneNumber;
    private Role role;
    private String restaurantName;
    private boolean active;
    private LocalDateTime createdAt;

    public AdminUserResponse() {
    }

    public AdminUserResponse(
            Long id,
            String email,
            String fullName,
            String phoneNumber,
            Role role,
            String restaurantName,
            boolean active,
            LocalDateTime createdAt
    ) {
        this.id = id;
        this.email = email;
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.role = role;
        this.restaurantName = restaurantName;
        this.active = active;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
