package yilee.beasm.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.sql.Date;
import java.time.ZonedDateTime;
import java.util.Map;

public class JwtUtils {
    public static String generateToken(Map<String, Object> claims, int min) {
        return  Jwts.builder()
                .header()
                .add("typ", "JWT")
                .add("alg", "RS256")
                .and()
                .issuedAt(Date.from(ZonedDateTime.now().toInstant()))
                .expiration(Date.from(ZonedDateTime.now().plusMinutes(min).toInstant()))
                .claims(claims)
                .signWith(JwtRsaGeneratorUtils.secretKey, SignatureAlgorithm.RS256)
                .compact();
    }

    public static Map<String, Object> validateToken(String token) {
        return  Jwts.parser()
                .verifyWith(JwtRsaGeneratorUtils.pubKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
