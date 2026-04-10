package com.food.foodorderingsystem.service;

import com.food.foodorderingsystem.dto.OrderItemResponse;
import com.food.foodorderingsystem.dto.OrderResponse;
import com.food.foodorderingsystem.dto.PlaceOrderItemRequest;
import com.food.foodorderingsystem.dto.PlaceOrderRequest;
import com.food.foodorderingsystem.entity.MenuItem;
import com.food.foodorderingsystem.entity.Order;
import com.food.foodorderingsystem.entity.OrderItem;
import com.food.foodorderingsystem.entity.OrderStatus;
import com.food.foodorderingsystem.entity.Role;
import com.food.foodorderingsystem.entity.User;
import com.food.foodorderingsystem.repository.MenuItemRepository;
import com.food.foodorderingsystem.repository.OrderRepository;
import com.food.foodorderingsystem.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final MenuItemRepository menuItemRepository;
    private final EmailNotificationService emailNotificationService;

    public OrderService(
            OrderRepository orderRepository,
            UserRepository userRepository,
            MenuItemRepository menuItemRepository,
            EmailNotificationService emailNotificationService
    ) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.menuItemRepository = menuItemRepository;
        this.emailNotificationService = emailNotificationService;
    }

    @Transactional
    public OrderResponse placeOrder(String customerEmail, PlaceOrderRequest request) {
        User customer = getUserByEmail(customerEmail);
        User restaurant = getUserById(request.getRestaurantId());

        if (restaurant.getRole() != Role.RESTAURANT)
            throw new RuntimeException("Selected account is not a restaurant");

        Order order = new Order();
        order.setUser(customer);
        order.setRestaurant(restaurant);
        order.setStatus(OrderStatus.PLACED);

        List<OrderItem> orderItems = request.getItems().stream()
                .map(itemRequest -> mapOrderItem(order, restaurant, itemRequest))
                .collect(Collectors.toList());

        BigDecimal totalAmount = orderItems.stream()
                .map(item -> item.getUnitPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        order.setOrderItems(orderItems);
        order.setTotalAmount(totalAmount);

        Order savedOrder = orderRepository.save(order);
        emailNotificationService.sendOrderPlacedEmail(customer.getEmail(), savedOrder.getId(), savedOrder.getTotalAmount());
        return toResponse(savedOrder);
    }

    public List<OrderResponse> getUserOrders(String customerEmail) {
        User customer = getUserByEmail(customerEmail);
        return orderRepository.findByUserId(customer.getId())
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public List<OrderResponse> getRestaurantOrders(String restaurantEmail) {
        User restaurant = getUserByEmail(restaurantEmail);
        return orderRepository.findByRestaurantId(restaurant.getId())
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public OrderResponse updateOrderStatus(Long orderId, String actorEmail, OrderStatus newStatus, boolean isAdmin) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        User actor = getUserByEmail(actorEmail);

        if (!isAdmin && !order.getRestaurant().getId().equals(actor.getId()))
            throw new RuntimeException("Unauthorized to update this order");

        order.setStatus(newStatus);
        Order updated = orderRepository.save(order);
        emailNotificationService.sendOrderStatusChangedEmail(updated.getUser().getEmail(), updated.getId(), updated.getStatus());
        return toResponse(updated);
    }

    public List<OrderResponse> getAllOrders() {
        return orderRepository.findAll().stream().map(this::toResponse).collect(Collectors.toList());
    }

    private OrderItem mapOrderItem(Order order, User restaurant, PlaceOrderItemRequest itemRequest) {
        MenuItem menuItem = menuItemRepository.findById(itemRequest.getMenuItemId())
                .orElseThrow(() -> new RuntimeException("Menu item not found: " + itemRequest.getMenuItemId()));

        if (!menuItem.getRestaurant().getId().equals(restaurant.getId()))
            throw new RuntimeException("Menu item does not belong to selected restaurant");

        if (!menuItem.isAvailable())
            throw new RuntimeException("Menu item is not available: " + menuItem.getName());

        OrderItem orderItem = new OrderItem();
        orderItem.setOrder(order);
        orderItem.setMenuItem(menuItem);
        orderItem.setQuantity(itemRequest.getQuantity());
        orderItem.setUnitPrice(menuItem.getPrice());
        return orderItem;
    }

    private OrderResponse toResponse(Order order) {
        OrderResponse response = new OrderResponse();
        response.setId(order.getId());
        response.setStatus(order.getStatus().name());
        response.setTotalAmount(order.getTotalAmount());
        response.setCustomerEmail(order.getUser().getEmail());
        response.setRestaurantName(getRestaurantDisplayName(order.getRestaurant()));
        response.setPlacedAt(order.getPlacedAt());
        response.setItems(order.getOrderItems().stream().map(this::toItemResponse).collect(Collectors.toList()));
        return response;
    }

    private OrderItemResponse toItemResponse(OrderItem orderItem) {
        OrderItemResponse itemResponse = new OrderItemResponse();
        itemResponse.setMenuItemId(orderItem.getMenuItem().getId());
        itemResponse.setMenuItemName(orderItem.getMenuItem().getName());
        itemResponse.setQuantity(orderItem.getQuantity());
        itemResponse.setUnitPrice(orderItem.getUnitPrice());
        return itemResponse;
    }

    private User getUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));
    }

    private User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
    }

    private String getRestaurantDisplayName(User restaurant) {
        if (restaurant.getRestaurantName() != null && !restaurant.getRestaurantName().isBlank())
            return restaurant.getRestaurantName();
        return restaurant.getFullName();
    }
}
