package com.food.foodorderingsystem.repository;

import com.food.foodorderingsystem.entity.Order;
import com.food.foodorderingsystem.entity.User;
import com.food.foodorderingsystem.entity.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByUserId(Long userId);

    List<Order> findByRestaurantId(Long restaurantId);

    List<Order> findByUser(User user);

    List<Order> findByRestaurant(User restaurant);

    List<Order> findByStatus(OrderStatus status);
}
