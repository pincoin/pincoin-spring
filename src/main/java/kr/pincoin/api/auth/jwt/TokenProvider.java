package kr.pincoin.api.auth.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.io.DecodingException;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.*;

import static kr.pincoin.api.auth.jwt.JwtFilter.*;


@Slf4j
@Component
public class TokenProvider {
    // JWT 암호화, 복호화 비밀키
    // HS256 알고리즘을 이용하므로 256비트(알파벳숫자 32자) 이상
    @Value("${auth.jwt-secret-key}")
    private String jwtSecretSignKey;

    public static final int ACCESS_TOKEN_EXPIRES_IN = 60 * 60; // 1시간
    public static final int REFRESH_TOKEN_EXPIRES_IN = 60 * 60 * 24 * 14; // 2주

    public String
    getXAuthToken(HttpServletRequest request) {
        // 헤더 형식
        // 비표준 미등록 헤더
        // X-Auth-Token : JWTString=
        final String header = request.getHeader("X-AUTH-TOKEN");

        if (header != null && !header.isBlank()) {
            return header;
        }
        return null;
    }

    public String
    getBearerToken(HttpServletRequest request) {
        // 헤더 형식
        // RFC 7235 표준 등록 헤더
        // Authorization: Bearer JWTString=
        final String header = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (header != null && header.startsWith("Bearer ")) {
            return header.split(" ")[1].trim();
        }

        return null;
    }

    public Optional<String>
    validateAccessToken(String token, HttpServletRequest request) {
        try {
            Jws<Claims> jws = Jwts.parserBuilder()
                    .setSigningKey(Decoders.BASE64.decode(jwtSecretSignKey)).build()
                    .parseClaimsJws(token);

            return Optional.ofNullable(jws.getBody().getSubject());
        } catch (SignatureException | DecodingException ignored) { // SignatureException deprecated, but not caught
            request.setAttribute("exception", ERROR_401_INVALID_SECRET_KEY);
        } catch (ExpiredJwtException ignored) {
            request.setAttribute("exception", ERROR_401_EXPIRED_JWT);
        } catch (UnsupportedJwtException | MalformedJwtException | SecurityException |
                 IllegalArgumentException ex) {
            log.warn(ex.getLocalizedMessage());
            request.setAttribute("exception", ERROR_401_INVALID_TOKEN);
        }

        return Optional.empty();
    }

    public String
    createAccessToken(Map<String, Object> claims) {
        // 액세스 토큰은 username 등 개인 정보 포함
        // 엑세스 토큰은 디비에 저장 안 함
        // 키 길이에 따라서 알고리즘 선택: (HS512: openssl rand -hex 64)
        SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecretSignKey));

        // 헤더
        Map<String, Object> headers = new HashMap<>();
        headers.put("typ", "JWT");
        headers.put("alg", "HS512");

        return Jwts.builder()
                .setHeader(headers)
                .setClaims(claims) // 클레임
                // .setSubject(username) // sub
                // .setId("1") // jti
                .setExpiration(Date.from(LocalDateTime.now()
                                                 .plus(Duration.of(ACCESS_TOKEN_EXPIRES_IN, ChronoUnit.SECONDS))
                                                 .atZone(ZoneId.systemDefault()).toInstant())) // exp
                .signWith(key)
                .compact();
    }

    public UUID
    createRefreshToken() {
        // 리프레시 토큰은 username 등 개인 정보 미포함
        // 리프레시 토큰은 디비에 저장

        // 장고 마이그레이션 char(32), 자바 36바이트 (대시 포함)
        return UUID.randomUUID();
    }
}