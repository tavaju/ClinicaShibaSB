package com.example.demo.security;

import java.security.Key;
import java.util.Date;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JWTGenerator {

    private static final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS512);
    public static final Long EXPIRATION_TIME = 7000000L;    /* Crear el JWT */
    public String generateToken(Authentication authentication) {
        /* Datos necesarios para la creación */
        String username = authentication.getName();
        Date currentDate = new Date();
        Date expireDate = new Date(currentDate.getTime() + EXPIRATION_TIME);

        /* Obtener rol del usuario */
        String role = authentication.getAuthorities().stream()
                .findFirst()
                .map(authority -> authority.getAuthority())
                .orElse("ROLE_USER");

        /* Crear el JWT */
        String token = Jwts.builder().setSubject(username)
                .setIssuedAt(currentDate)
                .setExpiration(expireDate)
                .claim("role", role)  // Agregar rol al token
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
        return token;
    }    /* Metodo para obtener el usuario a partir del JWT */
    public String getUserFromJwt(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody().getSubject();
    }

    /* Metodo para obtener el rol a partir del JWT */
    public String getRoleFromJwt(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody().get("role", String.class);
    }

    /* Metodo para validar el JWT */
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}