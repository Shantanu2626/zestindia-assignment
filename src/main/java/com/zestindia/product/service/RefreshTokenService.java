package com.zestindia.product.service;

import com.zestindia.product.model.RefreshToken;
import com.zestindia.product.model.User;
import com.zestindia.product.repository.RefreshTokenRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class RefreshTokenService {

    private final RefreshTokenRepository repository;

    public RefreshTokenService(RefreshTokenRepository repository) {
        this.repository = repository;
    }

    public String createRefreshToken(User user) {

        String token = UUID.randomUUID().toString();

        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setToken(token);
        refreshToken.setEmail(user.getEmail());
        refreshToken.setExpiryDate(LocalDateTime.now().plusDays(7));

        repository.save(refreshToken);
        return token;
    }

    public RefreshToken validateRefreshToken(String token) {

        RefreshToken refreshToken = repository.findByToken(token)
                .orElseThrow(() -> new RuntimeException("Invalid refresh token"));

        if (refreshToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            repository.delete(refreshToken);
            throw new RuntimeException("Refresh token expired");
        }

        return refreshToken;
    }

    public void deleteRefreshToken(String token) {
        repository.deleteByToken(token);
    }
}
