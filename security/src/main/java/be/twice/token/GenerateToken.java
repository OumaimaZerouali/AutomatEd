package be.twice.token;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

public class GenerateToken {

    public String generate(String userId, String role) {
        //Key secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS512);
        long expirationTimeMillis = 360000;

        return Jwts.builder()
                .setSubject(userId)
                .claim("role", role)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationTimeMillis))
                .signWith(SignatureAlgorithm.HS512, GenerateSecreKey.getSecretKey())
                .compact();
    }
}
