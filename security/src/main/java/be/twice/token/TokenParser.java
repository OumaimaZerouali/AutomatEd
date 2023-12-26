package be.twice.token;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;

public class TokenParser {
    public Claims getTokenPayload(String token) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(GenerateSecreKey.getSecretKey())
                    .parseClaimsJws(token)
                    .getBody();

            return claims;
        } catch (JwtException e) {
            throw new RuntimeException("Invalid token", e);
        }
    }
}
