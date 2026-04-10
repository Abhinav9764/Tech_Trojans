package com.food.foodorderingsystem.dto;

public class PublicRestaurantResponse {
    private Long id;
    private String name;
    private String imageUrl;
    private Double rating;
    private Integer deliveryMins;
    private Integer priceForTwo;
    private String[] tags;

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

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public Integer getDeliveryMins() {
        return deliveryMins;
    }

    public void setDeliveryMins(Integer deliveryMins) {
        this.deliveryMins = deliveryMins;
    }

    public Integer getPriceForTwo() {
        return priceForTwo;
    }

    public void setPriceForTwo(Integer priceForTwo) {
        this.priceForTwo = priceForTwo;
    }

    public String[] getTags() {
        return tags;
    }

    public void setTags(String[] tags) {
        this.tags = tags;
    }
}
