package project3.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtTokenUtil {
    //extract value from application.properties
    @Value("${SECRET_KEY}")
    private String secretKey;

    //generate JWT token
    public String generateToken(String username, String role) {
        SecretKey key = Keys.hmacShaKeyFor(secretKey.getBytes());
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 7200000))
                .claim("role", role)
                .signWith(SignatureAlgorithm.HS256, key)
                .compact();
    }

    public Claims getAllClaimsFromToken(String token) {
        SecretKey key = Keys.hmacShaKeyFor(secretKey.getBytes());
        Claims claims = Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getBody();
        return claims;
    }


    public String getUserNameFromToken(String token) {
        Claims claims = getAllClaimsFromToken(token);
        String userName = claims.getSubject();
        return userName;
    }

    public boolean isTokenValid(String token){
        try{
            getAllClaimsFromToken(token);
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}