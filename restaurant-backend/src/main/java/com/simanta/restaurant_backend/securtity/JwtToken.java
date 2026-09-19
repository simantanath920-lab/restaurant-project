package com.simanta.restaurant_backend.securtity;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
 
@Component
public class JwtToken {

    @Value("${jwt.secret}")
    private String SECRET_KEY;

    @Value("${jwt.expiration}")
    private int EXPIRATION_TOKEN_TIME;


    public String createToken(String email,String role){
        Map<String,Object> claims = new HashMap<>();
        claims.put("role", role);
        return generateToken(claims, email);
    }

    public String generateToken(Map<String,Object> claims,String subject){
        return Jwts.builder()
        .setClaims(claims)
        .setSubject(subject)
        .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TOKEN_TIME))
        .setIssuedAt(new Date())
        .signWith(SignatureAlgorithm.HS256,SECRET_KEY)
        .compact();
    }

    // Extract Email 
    public String extractEmail(String token){
        Claims claims = Jwts.parser()
            .setSigningKey(SECRET_KEY)
            .parseClaimsJws(token)
            .getBody();
            return claims.getSubject();
    }

    // Extarct Role
    public String extractRole(String token){
        Claims claims = Jwts.parser()
            .setSigningKey(SECRET_KEY)
            .parseClaimsJws(token)
            .getBody();
        return claims.get("role",String.class);
    }

    // Extract All Claims
    public Claims extractAllClaims(String token){
        return Jwts.parser()
            .setSigningKey(SECRET_KEY)
            .parseClaimsJws(token)
            .getBody();
    }

    // Extract Expiration
    public Date extractExpiration(String token){
        Claims claims = Jwts.parser()
            .setSigningKey(SECRET_KEY)
            .parseClaimsJws(token)
            .getBody();
        return claims.getExpiration();
    }

    // isTokenValid
    public boolean isTokenValid(String token){
        return extractExpiration(token).before(new Date());
    }

    // ValidateToken
    public boolean validateToken(String token,String email){
        String checkEmail = extractEmail(token);
        return checkEmail.equals(email) && !isTokenValid(token);
    }
   
    
}
