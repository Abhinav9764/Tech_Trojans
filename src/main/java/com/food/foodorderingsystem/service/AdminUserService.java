package com.food.foodorderingsystem.service;

import com.food.foodorderingsystem.dto.AdminUserResponse;
import com.food.foodorderingsystem.dto.AdminUserUpdateRequest;
import com.food.foodorderingsystem.entity.Role;
import com.food.foodorderingsystem.entity.User;
import com.food.foodorderingsystem.repository.CartItemRepository;
import com.food.foodorderingsystem.repository.CartRepository;
import com.food.foodorderingsystem.repository.MenuItemRepository;
import com.food.foodorderingsystem.repository.OrderRepository;
import com.food.foodorderingsystem.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdminUserService {

    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final MenuItemRepository menuItemRepository;
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final PasswordEncoder passwordEncoder;

    public AdminUserService(
            UserRepository userRepository,
            OrderRepository orderRepository,
            MenuItemRepository menuItemRepository,
            CartRepository cartRepository,
            CartItemRepository cartItemRepository,
            PasswordEncoder passwordEncoder
    ) {
        this.userRepository = userRepository;
        this.orderRepository = orderRepository;
        this.menuItemRepository = menuItemRepository;
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional(readOnly = true)
    public List<AdminUserResponse> listAllUsers() {
        return userRepository.findAll().stream()
                .sorted(Comparator.comparing(User::getId))
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public AdminUserResponse getUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return toResponse(user);
    }

    @Transactional
    public AdminUserResponse updateUser(Long id, AdminUserUpdateRequest request) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (request.getFullName() != null && !request.getFullName().isBlank()) {
            user.setFullName(request.getFullName().trim());
        }
        if (request.getEmail() != null && !request.getEmail().isBlank()) {
            String email = request.getEmail().trim().toLowerCase();
            if (!email.equalsIgnoreCase(user.getEmail()) && userRepository.existsByEmail(email)) {
                throw new RuntimeException("Email already in use");
            }
            user.setEmail(email);
        }
        if (request.getPhoneNumber() != null) {
            user.setPhoneNumber(request.getPhoneNumber().trim().isEmpty() ? null : request.getPhoneNumber().trim());
        }
        if (request.getRestaurantName() != null && user.getRole() == Role.RESTAURANT) {
            user.setRestaurantName(request.getRestaurantName().trim().isEmpty() ? null : request.getRestaurantName().trim());
        }
        if (request.getActive() != null) {
            user.setActive(request.getActive());
        }
        if (request.getNewPassword() != null && !request.getNewPassword().isBlank()) {
            if (request.getNewPassword().length() < 6) {
                throw new RuntimeException("Password must be at least 6 characters");
            }
            user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        }

        return toResponse(userRepository.save(user));
    }

    @Transactional
    public void deleteUser(Long targetId, String adminEmail) {
        User admin = userRepository.findByEmail(adminEmail)
                .orElseThrow(() -> new RuntimeException("Admin not found"));
        User target = userRepository.findById(targetId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (target.getId().equals(admin.getId())) {
            throw new RuntimeException("You cannot delete your own account");
        }
        if (target.getRole() == Role.ADMIN && userRepository.countByRole(Role.ADMIN) <= 1) {
            throw new RuntimeException("Cannot delete the last administrator");
        }

        if (target.getRole() == Role.RESTAURANT) {
            List<Long> menuIds = menuItemRepository.findByRestaurantId(target.getId()).stream()
                    .map(m -> m.getId())
                    .collect(Collectors.toList());
            if (!menuIds.isEmpty()) {
                cartItemRepository.deleteByMenuItemIdIn(menuIds);
            }
            orderRepository.deleteAll(orderRepository.findByRestaurantId(target.getId()));
            menuItemRepository.deleteAll(menuItemRepository.findByRestaurantId(target.getId()));
        }

        if (target.getRole() == Role.USER) {
            cartRepository.findByUserId(target.getId()).ifPresent(cartRepository::delete);
            orderRepository.deleteAll(orderRepository.findByUserId(target.getId()));
        }

        if (target.getRole() == Role.ADMIN) {
            cartRepository.findByUserId(target.getId()).ifPresent(cartRepository::delete);
        }

        userRepository.delete(target);
    }

    private AdminUserResponse toResponse(User user) {
        return new AdminUserResponse(
                user.getId(),
                user.getEmail(),
                user.getFullName(),
                user.getPhoneNumber(),
                user.getRole(),
                user.getRestaurantName(),
                user.isActive(),
                user.getCreatedAt()
        );
    }
}
