package com.eventsvk.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Collection;
import java.util.Date;
import java.util.Objects;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Service
public class JwtService {

    @Value("${security.jwt.secret}")
    private String secret;

    @Getter
    @Value("${security.jwt.expiration-seconds:3600}")
    private long expirationSeconds;

    private SecretKey signingKey;
    private JwtParser jwtParser;

    @PostConstruct
    private void init() {
        byte[] keyBytes = Decoders.BASE64.decode(Objects.requireNonNull(secret, "JWT secret must not be null"));
        this.signingKey = Keys.hmacShaKeyFor(keyBytes);
        this.jwtParser = Jwts.parser().verifyWith(signingKey).build();
    }

    public String generateToken(UserDetails userDetails) {
        Instant now = Instant.now();
        Instant expiry = now.plusSeconds(expirationSeconds);

        String roles = extractAuthorities(userDetails.getAuthorities());

        return Jwts.builder()
                .subject(userDetails.getUsername())
                .issuedAt(Date.from(now))
                .expiration(Date.from(expiry))
                .claim("roles", roles)
                .signWith(signingKey, Jwts.SIG.HS256)
                .compact();
    }

    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }

    public String extractRoles(String token) {
        var roles = extractAllClaims(token).get("roles");
        return roles != null ? roles.toString() : "";
    }

    private Claims extractAllClaims(String token) {
        return jwtParser
                .parseSignedClaims(token)
                .getPayload();
    }

    private String extractAuthorities(Collection<? extends GrantedAuthority> authorities) {
        if (authorities == null || authorities.isEmpty()) {
            return "";
        }
        var sb = new StringBuilder();
        for (GrantedAuthority authority : authorities) {
            if (!sb.isEmpty()) {
                sb.append(',');
            }
            sb.append(authority.getAuthority());
        }
        return sb.toString();
    }
}
