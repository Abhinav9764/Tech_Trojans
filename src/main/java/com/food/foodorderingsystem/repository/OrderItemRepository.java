package com.food.foodorderingsystem.repository;

import com.food.foodorderingsystem.entity.OrderItem;
import com.food.foodorderingsystem.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

    List<OrderItem> findByOrderId(Long orderId);

    List<OrderItem> findByOrder(Order order);
}
