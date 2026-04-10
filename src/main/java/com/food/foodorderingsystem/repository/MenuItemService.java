package com.food.foodorderingsystem.service;

import com.food.foodorderingsystem.config.DemoMediaUrls;
import com.food.foodorderingsystem.dto.MenuItemRequest;
import com.food.foodorderingsystem.dto.MenuItemResponse;
import com.food.foodorderingsystem.entity.MenuItem;
import com.food.foodorderingsystem.entity.User;
import com.food.foodorderingsystem.repository.MenuItemRepository;
import com.food.foodorderingsystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MenuItemService {

    @Autowired
    private MenuItemRepository menuItemRepository;

    @Autowired
    private UserRepository userRepository;

    public MenuItemResponse addMenuItem(Long restaurantId, MenuItemRequest request) {
        User restaurant = userRepository.findById(restaurantId)
                .orElseThrow(() -> new RuntimeException("Restaurant not found"));

        MenuItem menuItem = new MenuItem();
        menuItem.setName(request.getName());
        menuItem.setDescription(request.getDescription());
        menuItem.setPrice(request.getPrice());
        menuItem.setCategory(request.getCategory());
        menuItem.setImageUrl(request.getImageUrl());
        menuItem.setAvailable(request.isAvailable());
        menuItem.setRestaurant(restaurant);

        MenuItem saved = menuItemRepository.save(menuItem);
        return convertToResponse(saved);
    }

    public MenuItemResponse updateMenuItem(Long menuItemId, Long restaurantId, MenuItemRequest request) {
        MenuItem menuItem = menuItemRepository.findById(menuItemId)
                .orElseThrow(() -> new RuntimeException("Menu item not found"));

        // Verify this menu item belongs to the restaurant
        if (!menuItem.getRestaurant().getId().equals(restaurantId)) {
            throw new RuntimeException("Unauthorized: This menu item does not belong to your restaurant");
        }

        menuItem.setName(request.getName());
        menuItem.setDescription(request.getDescription());
        menuItem.setPrice(request.getPrice());
        menuItem.setCategory(request.getCategory());
        menuItem.setImageUrl(request.getImageUrl());
        menuItem.setAvailable(request.isAvailable());

        MenuItem updated = menuItemRepository.save(menuItem);
        return convertToResponse(updated);
    }

    public void deleteMenuItem(Long menuItemId, Long restaurantId) {
        MenuItem menuItem = menuItemRepository.findById(menuItemId)
                .orElseThrow(() -> new RuntimeException("Menu item not found"));

        if (!menuItem.getRestaurant().getId().equals(restaurantId)) {
            throw new RuntimeException("Unauthorized: This menu item does not belong to your restaurant");
        }

        menuItemRepository.delete(menuItem);
    }

    public List<MenuItemResponse> getMenuItemsByRestaurant(Long restaurantId) {
        List<MenuItem> menuItems = menuItemRepository.findByRestaurantId(restaurantId);
        return menuItems.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    public List<MenuItemResponse> getAvailableMenuItemsByRestaurant(Long restaurantId) {
        List<MenuItem> menuItems = menuItemRepository.findByRestaurantIdAndAvailableTrue(restaurantId);
        return menuItems.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    public MenuItemResponse getMenuItemById(Long id) {
        MenuItem menuItem = menuItemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Menu item not found"));
        return convertToResponse(menuItem);
    }

    public MenuItemResponse updateAvailability(Long menuItemId, Long restaurantId, boolean isAvailable) {
        MenuItem menuItem = menuItemRepository.findById(menuItemId)
                .orElseThrow(() -> new RuntimeException("Menu item not found"));

        if (!menuItem.getRestaurant().getId().equals(restaurantId)) {
            throw new RuntimeException("Unauthorized: This menu item does not belong to your restaurant");
        }

        menuItem.setAvailable(isAvailable);
        MenuItem updated = menuItemRepository.save(menuItem);
        return convertToResponse(updated);
    }

    private MenuItemResponse convertToResponse(MenuItem menuItem) {
        MenuItemResponse response = new MenuItemResponse();
        response.setId(menuItem.getId());
        response.setName(menuItem.getName());
        response.setDescription(menuItem.getDescription());
        response.setPrice(menuItem.getPrice());
        response.setCategory(menuItem.getCategory());
        response.setImageUrl(DemoMediaUrls.dishImageOrFallback(
                menuItem.getImageUrl(),
                menuItem.getCategory(),
                menuItem.getId(),
                menuItem.getName()));
        response.setAvailable(menuItem.isAvailable());
        response.setRestaurantName(menuItem.getRestaurant().getRestaurantName() != null ?
                                   menuItem.getRestaurant().getRestaurantName() :
                                   menuItem.getRestaurant().getFullName());
        response.setCreatedAt(menuItem.getCreatedAt());
        return response;
    }
}
