package com.food.foodorderingsystem.repository;

import com.food.foodorderingsystem.entity.User;
import com.food.foodorderingsystem.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

    Optional<User> findById(Long id);

    List<User> findByRole(Role role);

    long countByRole(Role role);
}
