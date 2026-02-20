package com.zestindia.product.security;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;

@Component
public class JwtFilterChain extends OncePerRequestFilter {

    private final GenerateJwt generateJwt;
    private final CustomUserDetails customUserDetails;

    public JwtFilterChain(GenerateJwt  generateJwt, CustomUserDetails customUserDetails){
        this.generateJwt = generateJwt;
        this.customUserDetails = customUserDetails;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException, ServletException, IOException {

        try {
            String authHeader = request.getHeader("Authorization");

            String token = null;

            String userName = null;

            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                token = authHeader.substring(7);
                userName = generateJwt.extractUserName(token);
                System.out.println(userName);
            }

            if (token != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = customUserDetails.loadUserByUsername(userName);
                if (generateJwt.isValid(token, userDetails)) {
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities()
                    );
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }
        } catch (Exception e){
            SecurityContextHolder.clearContext();
        }
        filterChain.doFilter(request , response);
    }
}
