package com.food.foodorderingsystem.controller;

import com.food.foodorderingsystem.dto.AuthRequest;
import com.food.foodorderingsystem.dto.AuthResponse;
import com.food.foodorderingsystem.dto.SignupRequest;
import com.food.foodorderingsystem.entity.User;
import com.food.foodorderingsystem.entity.Role;
import com.food.foodorderingsystem.repository.UserRepository;
import com.food.foodorderingsystem.security.JwtUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@Valid @RequestBody SignupRequest request) {

        // Check if email already exists
        if (userRepository.existsByEmail(request.getEmail())) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Email already exists!");
            return ResponseEntity.badRequest().body(error);
        }

        // Validate restaurant name if role is RESTAURANT
        if (request.getRole() == Role.RESTAURANT && (request.getRestaurantName() == null || request.getRestaurantName().trim().isEmpty())) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Restaurant name is required for RESTAURANT role");
            return ResponseEntity.badRequest().body(error);
        }

        // Create new user
        User user = new User();
        user.setFullName(request.getFullName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setPhoneNumber(request.getPhoneNumber());
        user.setRole(request.getRole() != null ? request.getRole() : Role.USER);
        user.setRestaurantName(request.getRestaurantName());
        user.setActive(true);

        userRepository.save(user);

        Map<String, String> response = new HashMap<>();
        response.put("message", "User registered successfully!");
        response.put("role", user.getRole().toString());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/signin")
    public ResponseEntity<?> signin(@Valid @RequestBody AuthRequest request) {
        try {
            authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Invalid email or password");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
        }

        User user = userRepository.findByEmail(request.getEmail()).get();
        String token = jwtUtil.generateToken(user.getEmail(), user.getRole().toString());

        return ResponseEntity.ok(new AuthResponse(token, user.getEmail(), user.getRole().toString(), user.getFullName()));
    }
}
