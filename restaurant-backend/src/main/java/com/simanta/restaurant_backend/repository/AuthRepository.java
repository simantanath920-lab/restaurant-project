package com.simanta.restaurant_backend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.simanta.restaurant_backend.model.User;

@Repository
public interface AuthRepository extends JpaRepository<User,Long>{

    Optional<User> findByEmail(String email);
    Boolean existsByEmailIgnoreCase(String email);
    Optional<User> findByVerificationToken(String token);
    Optional<User> findByResetToken(String token);
}
