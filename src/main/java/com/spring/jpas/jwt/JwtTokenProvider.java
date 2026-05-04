package com.spring.jpas.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecurityException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Slf4j
@Component
public class JwtTokenProvider {

    private final SecretKey key;
    private final long expireMillis;

    public JwtTokenProvider(
            @Value("${jwt.secret}") String secret,
            @Value("${jwt.accessTokenExpireMinutes:30}") long expireMinutes
    ) {
//        byte[] keyBytes = Decoders.BASE64.decode(secret);
//        this.key = Keys.hmacShaKeyFor(keyBytes); // 256bit(32byte) 미만이면 WeakKeyException [2](https://javadoc.io/static/io.jsonwebtoken/jjwt-api/0.11.2/io/jsonwebtoken/security/Keys.html)

        this.key = Keys.hmacShaKeyFor(
                secret.getBytes(java.nio.charset.StandardCharsets.UTF_8)
        );

        this.expireMillis = expireMinutes * 60_000L;
    }

    /** employeeId를 subject로 넣어서 토큰 발급 */
    public String createAccessToken(String employeeId) {
        Date now = new Date();
        Date expiry = new Date(now.getTime() + expireMillis);

        return Jwts.builder()
                .setSubject(employeeId)
                .setIssuedAt(now)
                .setExpiration(expiry)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact(); // setIssuedAt/setExpiration/signWith 패턴 [4](https://run-code-rich.tistory.com/entry/springboot-jwt-authentication-guide)[1](https://stackoverflow.com/questions/55102937/how-to-create-a-spring-security-key-for-signing-a-jwt-token)
    }

    public long getAccessTokenExpireSeconds() {
        // 30분
        long accessTokenExpireMillis = 30 * 60 * 1000L;
        return accessTokenExpireMillis / 1000;
    }

    public String getUserId(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean validate(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (SecurityException | MalformedJwtException e) {
            log.warn("Invalid JWT signature/token");
        } catch (ExpiredJwtException e) {
            log.warn("Expired JWT token");
        } catch (UnsupportedJwtException e) {
            log.warn("Unsupported JWT token");
        } catch (IllegalArgumentException e) {
            log.warn("JWT claims string is empty");
        }
        return false;
    }

    public long getExpireSeconds() {
        return expireMillis / 1000L;
    }
}
