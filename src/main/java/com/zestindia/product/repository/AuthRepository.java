package com.zestindia.product.repository;

import com.zestindia.product.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthRepository extends JpaRepository<User , Long> {
    Optional<User> findByEmail(String email);
}
