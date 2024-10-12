package com.hungnv.TheCoffeeHouse.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Component
public class JwtTokenProvider {

    @Value("${ACCESS_TOKEN_SECRET}")
    private String tokenSecret;

    private final long JWT_EXPIRATION = 1800000L; // 30 min

    public String generateToken(String email, String roleId) {

        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + JWT_EXPIRATION);

        String role;
        if (Objects.equals(roleId, "R2")) {
            role = "ROLE_STAFF";
        } else {
            role = "ROLE_ADMIN";
        }

        // Tạo JWT
        String token = Jwts.builder()
                .setSubject(email)
                .claim("roleId", role)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, tokenSecret)// Ký token
                .compact();
        return token;
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(tokenSecret).parseClaimsJws(token);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    public String getUsernameFromToken(String token) {
        Claims claims = Jwts.parser().setSigningKey(tokenSecret).parseClaimsJws(token).getBody();
        return claims.getSubject();
    }

    public String getRoleFromToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(tokenSecret)
                .parseClaimsJws(token)
                .getBody();

        return claims.get("roleId", String.class);
    }




}
