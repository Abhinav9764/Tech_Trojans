package com.food.foodorderingsystem.repository;

import com.food.foodorderingsystem.entity.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {

    Optional<Restaurant> findByName(String name);

    List<Restaurant> findByIsActiveTrue();

    List<Restaurant> findByCuisineType(String cuisineType);
}
