package com.food.foodorderingsystem.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public class PlaceOrderRequest {

    @NotNull(message = "Restaurant id is required")
    private Long restaurantId;

    @Valid
    @NotEmpty(message = "Order items are required")
    private List<PlaceOrderItemRequest> items;

    public Long getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(Long restaurantId) {
        this.restaurantId = restaurantId;
    }

    public List<PlaceOrderItemRequest> getItems() {
        return items;
    }

    public void setItems(List<PlaceOrderItemRequest> items) {
        this.items = items;
    }
}
