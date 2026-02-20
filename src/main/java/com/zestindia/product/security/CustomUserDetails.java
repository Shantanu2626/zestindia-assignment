package com.zestindia.product.security;
import com.zestindia.product.model.User;
import com.zestindia.product.repository.AuthRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetails implements UserDetailsService {

   private final AuthRepository authRepository;

   public CustomUserDetails(AuthRepository authRepository){
       this.authRepository = authRepository;
   }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> user = authRepository.findByEmail(email);

        if(user.isPresent()){
            User userObject = user.get();
            return org.springframework.security.core.userdetails.User
                    .builder()
                    .username(userObject.getEmail())
                    .password(userObject.getPassword())
                    .roles(String.valueOf(userObject.getRole()))
                    .build();
        }

        throw  new RuntimeException("User not found" +email);
    }
}
