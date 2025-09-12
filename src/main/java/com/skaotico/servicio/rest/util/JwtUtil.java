package com.skaotico.servicio.rest.util;

import com.skaotico.servicio.rest.usuario.model.Usuario;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;
import java.time.Instant;

@Component
public class JwtUtil {

    private final JwtEncoder jwtEncoder;
    private final JwtDecoder jwtDecoder;

    @Value("${jwt.expiration}")
    private long expirationTime;

    public JwtUtil(JwtEncoder jwtEncoder, @Qualifier("jwtDecoderBean") JwtDecoder jwtDecoder) {
        this.jwtEncoder = jwtEncoder;
        this.jwtDecoder = jwtDecoder;
    }

    public String generateToken(Usuario usuario) {
        Instant now = Instant.now();
        Instant expiry = now.plusMillis(expirationTime);

        JwtClaimsSet claimsSet = JwtClaimsSet.builder()
                .issuer("SkaoticoAPI")
                .issuedAt(now)
                .expiresAt(expiry)
                .subject(usuario.getEmail())
                .claim("usuEmail", usuario.getEmail())
                .claim("nombre", usuario.getNombre())
                .claim("apellido", usuario.getApellido())
                .build();

        return jwtEncoder.encode(JwtEncoderParameters.from(claimsSet))
                .getTokenValue();
    }

    public Jwt decodeToken(String token) {
        return jwtDecoder.decode(token);
    }

    public boolean validateToken(String token) {
        try {
            jwtDecoder.decode(token);
            return true;
        } catch (JwtException e) {
            return false;
        }
    }

    public String extractEmail(String token) {
        return decodeToken(token).getSubject();
    }

    public String extractClaim(String token, String claimName) {
        Object value = decodeToken(token).getClaim(claimName);
        return value != null ? value.toString() : null;
    }
}
