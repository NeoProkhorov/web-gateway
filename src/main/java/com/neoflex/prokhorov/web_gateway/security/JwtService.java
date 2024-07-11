package com.neoflex.prokhorov.web_gateway.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;
import java.util.function.Function;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class JwtService {
    static final long DAY_IN_MILLS = 86400000;
    static final int BEGIN_IX = 7;
    @Value("${key.secret}")
    String secretKey;

    public String generateToken(UserDetails user) {
        return Jwts.builder()
            .subject(user.getUsername())
            .issuedAt(new Date(System.currentTimeMillis()))
            .expiration(new Date(System.currentTimeMillis() + DAY_IN_MILLS))
            .signWith(getSecretKey())
            .compact();
    }

    public <T> T claimAndResolve(String token, Function<Claims, T> resolver) {
        Claims claims = claimToken(token);
        return resolver.apply(claims);
    }

    public String getUsername(String token) {
        token = token.substring(BEGIN_IX);
        return claimAndResolve(token, Claims::getSubject);
    }

    public boolean isValid(String token, UserDetails user) {
        String username = getUsername(token);
        return username.equals(user.getUsername()) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        token = token.substring(BEGIN_IX);
        return claimAndResolve(token, Claims::getExpiration).before(new Date());
    }

    private Claims claimToken(String token) {
        return Jwts.parser()
            .verifyWith(getSecretKey())
            .build()
            .parseSignedClaims(token)
            .getPayload();
    }

    private SecretKey getSecretKey() {
        byte[] keyBytes = Base64.getDecoder().decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
