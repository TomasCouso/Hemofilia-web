package project.hemofilia.servicios;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.crypto.SecretKey;
import java.security.Key;
import java.time.Instant;
import java.util.Date;

@Configuration
public class TokenServicio {

    @Value("${jwt.secret}")
    private String jwtSecret;

    private static Key SECRET_KEY;

    @PostConstruct
    public void init() {
        SECRET_KEY = Keys.hmacShaKeyFor(jwtSecret.getBytes());
    }

    public String generarToken(Long idCliente) {
        return Jwts.builder()
                .subject(idCliente.toString())
                .expiration(Date.from(Instant.now().plusSeconds(31536000)))  // 1 año de duración
                .issuedAt(Date.from(Instant.now()))  // Fecha de emisión del token
                .signWith(SECRET_KEY)
                .compact();
    }

    public Long obtenerIdClienteDesdeToken(String token) {
        return Long.parseLong(Jwts.parser()
                        .verifyWith((SecretKey) SECRET_KEY)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject());
    }
}