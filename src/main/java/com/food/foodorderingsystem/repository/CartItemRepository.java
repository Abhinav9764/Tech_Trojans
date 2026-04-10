package com.food.foodorderingsystem.repository;

import com.food.foodorderingsystem.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("DELETE FROM CartItem c WHERE c.menuItem.id IN :menuItemIds")
    void deleteByMenuItemIdIn(@Param("menuItemIds") List<Long> menuItemIds);
}
