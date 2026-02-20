package com.zestindia.product.controller;

import com.zestindia.product.dtos.AuthRequestDTO;
import com.zestindia.product.model.RefreshToken;
import com.zestindia.product.model.User;
import com.zestindia.product.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {


    private final AuthService authService;

    public AuthController(AuthService authService){
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody User user){
        User savedUser = authService.saveUser(user);
        return new ResponseEntity<>(savedUser , HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String , String>> userLogin(@RequestBody AuthRequestDTO authRequestDTO){
        Map<String , String> response = authService.userLogin(authRequestDTO);
        return new ResponseEntity<>(response , HttpStatus.OK);
    }

    //Refresh token
    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(@RequestBody Map<String, String> body) {

        String newAccessToken =
                authService.refreshAccessToken(body.get("refreshToken"));

        return ResponseEntity.ok(
                Map.of("accessToken", newAccessToken)
        );
    }
}
