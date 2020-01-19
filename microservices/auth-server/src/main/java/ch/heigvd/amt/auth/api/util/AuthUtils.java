package ch.heigvd.amt.auth.api.util;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.mindrot.jbcrypt.BCrypt;

import java.util.Base64;

public class AuthUtils {
    private static String KEY = "JDczMzcpZCk1OC0zeHJeIyNrZzQmKiEwbyRndihsYjZ0MGJoKEBAI2coNzgqMSh1KT0=";

    public static String createJWTString(Long userID, boolean role) {
        byte[] keyBytes = Base64.getDecoder().decode(KEY);

        // Generate signed JWT containing the user's id and role
        return Jwts.builder()
                .claim("userID", userID)
                .claim("role", role)
                .signWith(Keys.hmacShaKeyFor(keyBytes))
                .compact();
    }

    public static Jws<Claims> decodeJWTString(String jwt) {
        byte[] keyBytes = Base64.getDecoder().decode(KEY);

        try {
            return Jwts.parser()
                    .setSigningKey(Keys.hmacShaKeyFor(keyBytes))
                    .parseClaimsJws(jwt);
        } catch (JwtException e) {
            return null;
        }
    }

    public static String hashPassword(String plainTextPassword) {
        return BCrypt.hashpw(plainTextPassword, BCrypt.gensalt());
    }

    public static boolean checkPassword(String plainTextPassword, String hashedPassword) {
        try {
            return BCrypt.checkpw(plainTextPassword, hashedPassword);
        } catch (Exception e) {
            return false;
        }
    }
}
