package com.example.sd_frateanandreea_backend2.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {
    private static final String SECRET_KEY = "387aeb123e86dab6322e76dd9af665b56b5e096109def2d6488a338d00839d86";

    public String extractUserRole(String token) {
        final Claims claims = extractAllClaims(token);
        return claims.get("user_role", String.class);
    }

    public boolean isTokenValid(String token) {
            try {
                Jwts.parserBuilder().setSigningKey(getSignInKey()).build().parseClaimsJws(token);
                return true;
            } catch (JwtException | IllegalArgumentException e) {

                return false;
            }
        }

    private Key getSignInKey() {
            byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
            return Keys.hmacShaKeyFor(keyBytes);
    }

    private Claims extractAllClaims(String token){
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}



