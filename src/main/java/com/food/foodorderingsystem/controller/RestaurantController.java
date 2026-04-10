package com.food.foodorderingsystem.controller;

import com.food.foodorderingsystem.dto.MenuItemRequest;
import com.food.foodorderingsystem.dto.MenuItemResponse;
import com.food.foodorderingsystem.entity.User;
import com.food.foodorderingsystem.repository.UserRepository;
import com.food.foodorderingsystem.service.MenuItemService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/restaurant")
public class RestaurantController {

    @Autowired
    private MenuItemService menuItemService;

    @Autowired
    private UserRepository userRepository;

    // Get restaurant ID from authenticated user
    private Long getRestaurantId(UserDetails userDetails) {
        User user = userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));
        return user.getId();
    }

    // Add new menu item
    @PostMapping("/menu")
    public ResponseEntity<?> addMenuItem(@Valid @RequestBody MenuItemRequest request,
                                         @AuthenticationPrincipal UserDetails userDetails) {
        try {
            Long restaurantId = getRestaurantId(userDetails);
            MenuItemResponse response = menuItemService.addMenuItem(restaurantId, request);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    // Update menu item
    @PutMapping("/menu/{menuItemId}")
    public ResponseEntity<?> updateMenuItem(@PathVariable Long menuItemId,
                                            @Valid @RequestBody MenuItemRequest request,
                                            @AuthenticationPrincipal UserDetails userDetails) {
        try {
            Long restaurantId = getRestaurantId(userDetails);
            MenuItemResponse response = menuItemService.updateMenuItem(menuItemId, restaurantId, request);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    // Delete menu item
    @DeleteMapping("/menu/{menuItemId}")
    public ResponseEntity<?> deleteMenuItem(@PathVariable Long menuItemId,
                                            @AuthenticationPrincipal UserDetails userDetails) {
        try {
            Long restaurantId = getRestaurantId(userDetails);
            menuItemService.deleteMenuItem(menuItemId, restaurantId);
            Map<String, String> response = new HashMap<>();
            response.put("message", "Menu item deleted successfully");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    // Get all menu items for my restaurant
    @GetMapping("/menu")
    public ResponseEntity<?> getMyMenuItems(@AuthenticationPrincipal UserDetails userDetails) {
        try {
            Long restaurantId = getRestaurantId(userDetails);
            List<MenuItemResponse> menuItems = menuItemService.getMenuItemsByRestaurant(restaurantId);
            return ResponseEntity.ok(menuItems);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    // Update menu item availability (in-stock/out-of-stock)
    @PatchMapping("/menu/{menuItemId}/availability")
    public ResponseEntity<?> updateAvailability(@PathVariable Long menuItemId,
                                                @RequestParam boolean available,
                                                @AuthenticationPrincipal UserDetails userDetails) {
        try {
            Long restaurantId = getRestaurantId(userDetails);
            MenuItemResponse response = menuItemService.updateAvailability(menuItemId, restaurantId, available);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }
}
