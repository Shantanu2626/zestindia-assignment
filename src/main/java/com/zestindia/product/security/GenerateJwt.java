package com.zestindia.product.security;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import javax.crypto.SecretKey;
import java.util.Date;
import java.util.function.Function;

@Service
public class GenerateJwt {

    @Autowired
    private Logout logout;

    private final String SECRET = "my-super-secret-key-that-is-long-enough--1234567890";
    private final SecretKey key = Keys.hmacShaKeyFor(SECRET.getBytes());

    public String generateToken(String username , String role ,Long id) {
        return Jwts.builder()
                .claims()
                .add("id", id)
                .add("role" , role)
                .subject(username)
                .issuer("Shantanu Jape")
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 24 * 60 * 60 * 1000L))
                .and()
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public Claims extractAllClaims(String token){
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public <T> T extractClaims(String token , Function<Claims , T> getClaims){
        final Claims claims = extractAllClaims(token);
        return getClaims.apply(claims);
    }

    public String extractRole(String token){
        return (String) extractClaims(token , claims -> claims.get("role"));
    }

    public String extractUserName(String token){
        return extractClaims(token , Claims::getSubject);
    }

    public Date extractExpiration(String token){
        return extractClaims(token , claims -> claims.getExpiration());
    }

    public Boolean isExpired(String token){
        return  extractExpiration(token).before(new Date());
    }

    public Boolean isValid(String token, UserDetails userDetails){
        final String userName = extractUserName(token);
        return (userName.equals(userDetails.getUsername()) && !isExpired(token) && !logout.isTokenBlackList(token));
    }
}
