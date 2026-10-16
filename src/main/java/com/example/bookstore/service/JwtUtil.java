package com.example.bookstore.service;

import org.springframework.stereotype.Component;

import com.example.bookstore.model.Customer;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.Claims;


import java.util.Date;
import javax.crypto.SecretKey;


@Component 
public class JwtUtil {

     private final SecretKey secretKey = Keys.hmacShaKeyFor(
            "your-super-secret-key-change-this-your-super-secret-key".getBytes()
    );

    private final long expirationMs = 1000 * 60 * 60; // 1 hour

    public String generateToken(Customer customer) {
        return Jwts.builder()
                .setSubject(customer.getEmail())
                .claim("role", customer.getRole())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationMs))
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    public String extractEmail(String token) {
        return extractAllClaims(token).getSubject();
    }

    public String extractRole(String token) {
        return extractAllClaims(token).get("role", String.class);
    }

    public boolean isTokenValid(String token) {
        try {
            extractAllClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // ✔ REQUIRED helper method
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}