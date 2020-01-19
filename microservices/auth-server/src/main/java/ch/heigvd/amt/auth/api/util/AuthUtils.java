package ch.heigvd.amt.auth.api.util;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;

import java.security.Key;

public class AuthUtils {
    private static Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    public static String createJWTString(Long userID, boolean role) {
        // Generate signed JWT containing the user's id and role
        return Jwts.builder()
                .claim("userID", userID)
                .claim("role", role)
                .signWith(key)
                .compact();
    }

    public static Jws<Claims> decodeJWTString(String jwt) {
        try {
            return Jwts.parser()
                    .setSigningKey(key)
                    .parseClaimsJws(jwt);
        } catch (JwtException e) {
            // If exception was thrown, this JWT cannot be trusted
            return null;
        }
    }
}
