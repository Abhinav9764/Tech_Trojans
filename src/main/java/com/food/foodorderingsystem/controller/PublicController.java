package com.food.foodorderingsystem.controller;

import com.food.foodorderingsystem.config.DemoMediaUrls;
import com.food.foodorderingsystem.dto.MenuItemResponse;
import com.food.foodorderingsystem.dto.PublicRestaurantResponse;
import com.food.foodorderingsystem.entity.Role;
import com.food.foodorderingsystem.entity.User;
import com.food.foodorderingsystem.repository.UserRepository;
import com.food.foodorderingsystem.service.MenuItemService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/public")
public class PublicController {

    private final UserRepository userRepository;
    private final MenuItemService menuItemService;

    public PublicController(UserRepository userRepository, MenuItemService menuItemService) {
        this.userRepository = userRepository;
        this.menuItemService = menuItemService;
    }

    @GetMapping("/restaurants")
    public List<PublicRestaurantResponse> getRestaurants() {
        List<User> restaurants = userRepository.findByRole(Role.RESTAURANT);

        return restaurants.stream().map(restaurant -> {
            PublicRestaurantResponse response = new PublicRestaurantResponse();
            response.setId(restaurant.getId());
            response.setName(
                    restaurant.getRestaurantName() != null && !restaurant.getRestaurantName().isBlank()
                            ? restaurant.getRestaurantName()
                            : restaurant.getFullName()
            );
            response.setImageUrl(DemoMediaUrls.restaurantCover(restaurant.getId()));
            response.setRating(4.4);
            response.setDeliveryMins(30);
            response.setPriceForTwo(400);
            response.setTags(new String[]{"Popular", "Fast Delivery"});
            return response;
        }).toList();
    }

    @GetMapping("/restaurants/{restaurantId}/menu")
    public List<MenuItemResponse> getRestaurantMenu(@PathVariable Long restaurantId) {
        return menuItemService.getAvailableMenuItemsByRestaurant(restaurantId);
    }
}
