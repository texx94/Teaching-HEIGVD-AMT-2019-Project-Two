package ch.heigvd.amt.movie.api.util;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;

import java.util.Base64;

public class AuthUtils {
    private static String KEY = "JDczMzcpZCk1OC0zeHJeIyNrZzQmKiEwbyRndihsYjZ0MGJoKEBAI2coNzgqMSh1KT0=";

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
}
