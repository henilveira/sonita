package com.henilveira.sonita.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.henilveira.sonita.domain.user.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TokenService {

    @Value("${api.security.secret.token}")
    private String secret;

    public String generateAccessToken(User user) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);

            // Recupera as roles do usuário (ROLE_ADMIN, ROLE_USER)
            List<String> roles = user.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toList());

            return JWT.create()
                    .withIssuer("sonita")
                    .withSubject(user.getUsername())
                    .withClaim("roles", roles) // 🔹 Inclui as roles no token
                    .withExpiresAt(genExpirationDateAccess())
                    .sign(algorithm);
        } catch (JWTCreationException e) {
            throw new RuntimeException(e);
        }
    }

    public String generateRefreshToken(User user) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);

            // Recupera as roles do usuário (ROLE_ADMIN, ROLE_USER)
            List<String> roles = user.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toList());

            return JWT.create()
                    .withIssuer("sonita")
                    .withSubject(user.getUsername())
                    .withClaim("roles", roles) // 🔹 Inclui as roles no token
                    .withExpiresAt(genExpirationDateRefresh())
                    .sign(algorithm);
        } catch (JWTCreationException e) {
            throw new RuntimeException(e);
        }
    }

    public String validateToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                    .withIssuer("sonita")
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTDecodeException e) {
            return "";
        }
    }

    private Instant genExpirationDateRefresh() {
        return LocalDateTime.now().plusMonths(3).toInstant(ZoneOffset.of("-03:00"));
    }

    private Instant genExpirationDateAccess() {
        return LocalDateTime.now().plusMinutes(15).toInstant(ZoneOffset.of("-03:00"));
    }
}