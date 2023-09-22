package br.com.pitang.user.car.api.util;

import br.com.pitang.user.car.api.model.entity.User;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Slf4j
@Component
public class JwtUtils {

    @Value("${user.car.api.jwtSecret}")
    private String jwtSecret;

    @Value("${user.car.api.jwtExpirationMinutes}")
    private int jwtExpirationMinutes;

    public String generateJwtToken(Authentication authentication) {

        var user = (User) authentication.getPrincipal();

        try {
            Algorithm algorithm = Algorithm.HMAC256(jwtSecret);
            return JWT.create()
                    .withIssuer("auth-api")
                    .withSubject(user.getUsername())
                    .withExpiresAt(genExpirationDate())
                    .sign(algorithm);
        } catch (JWTCreationException exception) {
            throw new RuntimeException("Error while generating token", exception);
        }
    }
    private Instant genExpirationDate() {
        return LocalDateTime.now().plusMinutes(jwtExpirationMinutes).toInstant(ZoneOffset.of("-03:00"));
    }

    public String getLoginInTokenJWT(String authToken) {

            Algorithm algorithm = Algorithm.HMAC256(jwtSecret);
            return JWT.require(algorithm)
                    .withIssuer("auth-api")
                    .build()
                    .verify(authToken)
                    .getSubject();
    }
}