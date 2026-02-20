package com.zestindia.product.service;
import com.zestindia.product.dtos.AuthRequestDTO;
import com.zestindia.product.exception.PasswordIsIncorrect;
import com.zestindia.product.exception.UserConflictException;
import com.zestindia.product.exception.UserNotFoundException;
import com.zestindia.product.model.User;
import com.zestindia.product.repository.AuthRepository;
import com.zestindia.product.security.GenerateJwt;
import com.zestindia.product.security.Logout;
import org.springframework.security.core.Authentication;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class AuthService {


    private final AuthRepository authRepository;
    private final  PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final GenerateJwt generateJwt;
    private final Logout logout;
    private final RefreshTokenService refreshTokenService;

    public AuthService(AuthRepository authRepository , PasswordEncoder passwordEncoder , AuthenticationManager authenticationManager,GenerateJwt generateJwt , RefreshTokenService refreshTokenService, Logout logout){
        this.authRepository = authRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.generateJwt = generateJwt;
        this.refreshTokenService = refreshTokenService;
        this.logout = logout;
    }

    //save user in database
    public User saveUser(User user){
        Optional<User> dbUser = authRepository.findByEmail(user.getEmail());
        if(dbUser.isPresent()){
            throw new UserConflictException("User is already present please log in");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return authRepository.save(user);
    }

   //Login endpoint for users
    public Map<String , String> userLogin(AuthRequestDTO authRequestDTO){
        Optional<User> dbUser = authRepository.findByEmail(authRequestDTO.getEmail());
        if(dbUser.isEmpty()){
            throw new UserNotFoundException("User is not found please check main address");
        }
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequestDTO.getEmail() , authRequestDTO.getPassword())
        );

        String accessToken = generateJwt.generateToken(
                dbUser.get().getEmail(),
                dbUser.get().getRole().toString(),
                dbUser.get().getId()
        );

        String refreshToken = refreshTokenService.createRefreshToken(dbUser.get());

        Map<String , String> response = new HashMap<>();
        response.put("accesstoken",accessToken);
        response.put("refreshtoken",refreshToken);

        if(auth.isAuthenticated()){
            return response;
        }
        throw new PasswordIsIncorrect("Password is incorrect");
    }

    //Refresh token
    public String refreshAccessToken(String refreshToken) {
        var token =
                refreshTokenService.validateRefreshToken(refreshToken);

        User user = authRepository.findByEmail(token.getEmail())
                .orElseThrow(() ->
                        new UserNotFoundException("User not found"));
        return generateJwt.generateToken(
                user.getEmail(),
                user.getRole().toString(),
                user.getId()
        );
    }
}
