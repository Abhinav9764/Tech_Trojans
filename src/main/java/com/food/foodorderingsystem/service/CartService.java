package com.food.foodorderingsystem.service;

import com.food.foodorderingsystem.dto.CartItemRequest;
import com.food.foodorderingsystem.dto.CartItemResponse;
import com.food.foodorderingsystem.dto.CartResponse;
import com.food.foodorderingsystem.dto.OrderResponse;
import com.food.foodorderingsystem.dto.PlaceOrderItemRequest;
import com.food.foodorderingsystem.dto.PlaceOrderRequest;
import com.food.foodorderingsystem.entity.Cart;
import com.food.foodorderingsystem.entity.CartItem;
import com.food.foodorderingsystem.entity.MenuItem;
import com.food.foodorderingsystem.entity.User;
import com.food.foodorderingsystem.repository.CartRepository;
import com.food.foodorderingsystem.repository.MenuItemRepository;
import com.food.foodorderingsystem.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CartService {

    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final MenuItemRepository menuItemRepository;
    private final OrderService orderService;

    public CartService(
            CartRepository cartRepository,
            UserRepository userRepository,
            MenuItemRepository menuItemRepository,
            OrderService orderService
    ) {
        this.cartRepository = cartRepository;
        this.userRepository = userRepository;
        this.menuItemRepository = menuItemRepository;
        this.orderService = orderService;
    }

    @Transactional
    public CartResponse addItem(String userEmail, CartItemRequest request) {
        Cart cart = getOrCreateCart(userEmail);
        MenuItem menuItem = getMenuItem(request.getMenuItemId());

        Optional<CartItem> existing = cart.getItems()
                .stream()
                .filter(item -> item.getMenuItem().getId().equals(menuItem.getId()))
                .findFirst();

        if (existing.isPresent()) {
            CartItem cartItem = existing.get();
            cartItem.setQuantity(cartItem.getQuantity() + request.getQuantity());
        } else {
            CartItem cartItem = new CartItem();
            cartItem.setCart(cart);
            cartItem.setMenuItem(menuItem);
            cartItem.setQuantity(request.getQuantity());
            cart.getItems().add(cartItem);
        }

        return toResponse(cartRepository.save(cart));
    }

    @Transactional
    public CartResponse updateItemQuantity(String userEmail, Long menuItemId, Integer quantity) {
        Cart cart = getOrCreateCart(userEmail);
        CartItem cartItem = cart.getItems().stream()
                .filter(item -> item.getMenuItem().getId().equals(menuItemId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Item not found in cart"));

        cartItem.setQuantity(quantity);
        return toResponse(cartRepository.save(cart));
    }

    @Transactional
    public CartResponse removeItem(String userEmail, Long menuItemId) {
        Cart cart = getOrCreateCart(userEmail);
        cart.setItems(cart.getItems().stream()
                .filter(item -> !item.getMenuItem().getId().equals(menuItemId))
                .collect(Collectors.toCollection(ArrayList::new)));
        return toResponse(cartRepository.save(cart));
    }

    @Transactional(readOnly = true)
    public CartResponse getCart(String userEmail) {
        Cart cart = getOrCreateCart(userEmail);
        return toResponse(cart);
    }

    @Transactional
    public void clearCart(String userEmail) {
        Cart cart = getOrCreateCart(userEmail);
        cart.getItems().clear();
        cartRepository.save(cart);
    }

    @Transactional
    public OrderResponse checkout(String userEmail) {
        Cart cart = getOrCreateCart(userEmail);
        if (cart.getItems().isEmpty()) throw new RuntimeException("Cart is empty");

        Long restaurantId = cart.getItems().get(0).getMenuItem().getRestaurant().getId();
        boolean hasDifferentRestaurant = cart.getItems().stream()
                .anyMatch(item -> !item.getMenuItem().getRestaurant().getId().equals(restaurantId));

        if (hasDifferentRestaurant) throw new RuntimeException("All cart items must belong to the same restaurant");

        PlaceOrderRequest request = new PlaceOrderRequest();
        request.setRestaurantId(restaurantId);
        request.setItems(
                cart.getItems().stream().map(item -> {
                    PlaceOrderItemRequest orderItem = new PlaceOrderItemRequest();
                    orderItem.setMenuItemId(item.getMenuItem().getId());
                    orderItem.setQuantity(item.getQuantity());
                    return orderItem;
                }).collect(Collectors.toList())
        );

        OrderResponse response = orderService.placeOrder(userEmail, request);
        cart.getItems().clear();
        cartRepository.save(cart);
        return response;
    }

    private Cart getOrCreateCart(String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return cartRepository.findByUserId(user.getId()).orElseGet(() -> {
            Cart cart = new Cart();
            cart.setUser(user);
            return cartRepository.save(cart);
        });
    }

    private MenuItem getMenuItem(Long menuItemId) {
        MenuItem menuItem = menuItemRepository.findById(menuItemId)
                .orElseThrow(() -> new RuntimeException("Menu item not found"));
        if (!menuItem.isAvailable()) throw new RuntimeException("Menu item is not available");
        return menuItem;
    }

    private CartResponse toResponse(Cart cart) {
        List<CartItemResponse> items = cart.getItems().stream().map(item -> {
            CartItemResponse response = new CartItemResponse();
            response.setMenuItemId(item.getMenuItem().getId());
            response.setMenuItemName(item.getMenuItem().getName());
            response.setQuantity(item.getQuantity());
            response.setUnitPrice(item.getMenuItem().getPrice());
            response.setLineTotal(item.getMenuItem().getPrice().multiply(BigDecimal.valueOf(item.getQuantity())));
            return response;
        }).collect(Collectors.toList());

        BigDecimal totalAmount = items.stream()
                .map(CartItemResponse::getLineTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        CartResponse response = new CartResponse();
        response.setCartId(cart.getId());
        response.setUserEmail(cart.getUser().getEmail());
        response.setItems(items);
        response.setTotalAmount(totalAmount);
        return response;
    }
}
