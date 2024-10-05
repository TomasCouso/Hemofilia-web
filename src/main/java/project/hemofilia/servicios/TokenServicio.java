package project.hemofilia.servicios;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.security.Key;
import java.time.Instant;
import java.util.Date;

@Service
public class TokenServicio {

    // Utiliza una clave secreta segura generada con 'Keys.hmacShaKeyFor'
    private static final Key SECRET_KEY = Keys.hmacShaKeyFor("miClaveSecretaSeguraQueDebeSerDeAlMenos32Caracteres".getBytes());

    public String generarToken(Long idCliente) {
        return Jwts.builder()
                .subject(idCliente.toString())
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