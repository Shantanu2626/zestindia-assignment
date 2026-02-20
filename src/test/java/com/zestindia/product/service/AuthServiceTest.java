package com.zestindia.product.service;

import com.zestindia.product.dtos.AuthRequestDTO;
import com.zestindia.product.model.Role;
import com.zestindia.product.model.User;
import com.zestindia.product.repository.AuthRepository;
import com.zestindia.product.security.GenerateJwt;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;

import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private AuthRepository authRepository;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private GenerateJwt generateJwt;

    @Mock
    private RefreshTokenService refreshTokenService;

    @InjectMocks
    private AuthService authService;

    @Test
    void userLogin_success() {
        // GIVEN
        AuthRequestDTO dto = new AuthRequestDTO();
        dto.setEmail("admin@test.com");
        dto.setPassword("admin123");

        User user = new User();
        user.setEmail("admin@test.com");
        user.setRole(Role.ADMIN);
        user.setId(1L);

        // Mock repository
        when(authRepository.findByEmail(dto.getEmail()))
                .thenReturn(Optional.of(user));

        // Mock Authentication
        Authentication authentication = mock(Authentication.class);
        when(authentication.isAuthenticated()).thenReturn(true);
        when(authenticationManager.authenticate(any()))
                .thenReturn(authentication);

        // Mock JWT Generation
        when(generateJwt.generateToken(any(), any(), any()))
                .thenReturn("access-token");

        // Mock Refresh Token
        when(refreshTokenService.createRefreshToken(any()))
                .thenReturn("refresh-token");

        // WHEN
        Map<String, String> result = authService.userLogin(dto);

        // THEN
        assertNotNull(result);

        assertEquals("access-token", result.get("accesstoken"), "Key 'accesstoken' mismatch");
        assertEquals("refresh-token", result.get("refreshtoken"), "Key 'refreshtoken' mismatch");

        // Verify that internal calls happened with correct logic
        verify(generateJwt).generateToken(eq("admin@test.com"), eq("ADMIN"), eq(1L));
    }
}