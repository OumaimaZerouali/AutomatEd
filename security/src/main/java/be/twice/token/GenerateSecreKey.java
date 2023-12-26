package be.twice.token;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.security.Key;

public class GenerateSecreKey {
    private static final Key secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS512);

    public static Key getSecretKey() {
        return secretKey;
    }
}
