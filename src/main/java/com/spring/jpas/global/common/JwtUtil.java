package com.spring.jpas.global.common;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

public class JwtUtil {

    private static final String SECRET_KEY = "your-secret-key";

    public static Long getUserId(String token) {

        Claims claims = Jwts.parser()
                .setSigningKey(SECRET_KEY.getBytes())
                .parseClaimsJws(token)
                .getBody();

        return Long.valueOf(claims.get("employeeId").toString());
    }
}
