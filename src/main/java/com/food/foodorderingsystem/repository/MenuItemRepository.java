package com.food.foodorderingsystem.repository;

import com.food.foodorderingsystem.entity.MenuItem;
import com.food.foodorderingsystem.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenuItemRepository extends JpaRepository<MenuItem, Long> {

    List<MenuItem> findByRestaurantId(Long restaurantId);

    List<MenuItem> findByRestaurant(User restaurant);

    List<MenuItem> findByAvailableTrue();

    List<MenuItem> findByRestaurantIdAndAvailableTrue(Long restaurantId);
}
