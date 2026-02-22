package com.posts.demo.services;

import com.posts.demo.entities.UserEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.antlr.v4.runtime.Token;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Date;

@Service
public class JwtService {
    @Value("${jwt.servicekey}")
    private String secretKey;

   private SecretKey getSecretKey() {
        return Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
   }
    public String generateAccessToken(UserEntity user){
         long expireTimeInMilliSeconds=System.currentTimeMillis()+ (60*1000*10);
        return Jwts.builder()
                .subject(user.getId().toString())
                .claim("email",user.getEmail())
                .claim("username",user.getUsername())
                .claim("roles",user.getRoles())
                .issuedAt(new Date())
                .expiration(new Date(expireTimeInMilliSeconds))
                .signWith(getSecretKey())
                .compact();

    }
    public String generateRefreshToken(UserEntity user){
        long expireTimeInMilliSeconds=System.currentTimeMillis()+ (1000L * 60 * 60 * 24 * 30);
        return Jwts.builder()
                .subject(user.getId().toString())
                .issuedAt(new Date())
                .expiration(new Date(expireTimeInMilliSeconds))
                .signWith(getSecretKey())
                .compact();

    }
    public Long getUserIdFromToken(String token)
    {
        Claims claims = (Claims) Jwts.parser()
                .verifyWith(getSecretKey())
                .build().parseClaimsJws(token)
                .getPayload();

        return Long.valueOf(claims.getSubject());
    }

}
