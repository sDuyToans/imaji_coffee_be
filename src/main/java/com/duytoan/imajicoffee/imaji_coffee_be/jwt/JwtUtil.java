package com.duytoan.imajicoffee.imaji_coffee_be.jwt;

import com.duytoan.imajicoffee.imaji_coffee_be.security.CustomUserDetails;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class JwtUtil {
    private static final long EXPIRATION_TIME = 86400000; // 1 DAY
    @Value("${jwt.secret}")
    private String secret;

    private SecretKey key;


    @PostConstruct
    public void init(){
        this.key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    // Generate JWT with roles and expiration
    public String generateToken(CustomUserDetails userDetails){
        String roles = userDetails.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

      return Jwts.builder()
              .setSubject(userDetails.getEmail())
              .claim("username", userDetails.getUsername())
              .claim("roles", roles)
              .setIssuedAt(new Date())
              .setExpiration(new Date(System.currentTimeMillis()+ EXPIRATION_TIME))
              .signWith(key, SignatureAlgorithm.HS256)
              .compact();
    }

    // Extract any claims with a custom resolver
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        return claimsResolver.apply(extractAllClaims(token));
    }

    // Extract email / username from JWT
    public String extractUserName(String token){
        return extractClaim(token, Claims::getSubject);
    }

    // Extract roles from JWT
    public String extractRoles(String token){
        return extractClaim(token, claims -> claims.get("roles", String.class));
    }


    // Validate token by username and expiration
    public boolean isTokenValid(String token, UserDetails userDetails){
        final String username = extractUserName(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    // Check if token is expired
    public boolean isTokenExpired(String token){
        return extractExpiration(token).before(new Date());
    }

    // extract expiration date
    private Date extractExpiration(String token){
        return extractClaim(token, Claims::getExpiration);
    }

    // Extract all claims after verifying signature
    private Claims extractAllClaims(String token){
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
