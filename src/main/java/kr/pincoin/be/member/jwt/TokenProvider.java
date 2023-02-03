package kr.pincoin.be.member.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.io.DecodingException;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static kr.pincoin.be.auth.service.UserService.TOKEN_EXPIRES_IN;

@Slf4j
@Component
public class TokenProvider {
    private final Environment env;

    public TokenProvider(Environment env) {
        this.env = env;
    }

    public String getXAuthToken(HttpServletRequest request) {
        // 헤더 형식
        // 비표준 미등록 헤더
        // X-Auth-Token : dadas123sad12
        final String header = request.getHeader("X-AUTH-TOKEN");

        if (header != null && !header.isBlank()) {
            return header;
        }
        return null;
    }

    public String getBearerToken(HttpServletRequest request) {
        // 헤더 형식
        // RFC 7235 표준 등록 헤더
        // Authorization: Bearer QWxhZGRpbjpvcGVuIHNlc2FtZQ==
        final String header = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (header != null && header.startsWith("Bearer ")) {
            return header.split(" ")[1].trim();
        }

        return null;
    }

    public Optional<String> getUsername(String token) {
        try {
            Jws<Claims> jws = Jwts.parserBuilder()
                    .setSigningKey(Decoders.BASE64.decode(env.getProperty("jwt.secret-sign-key"))).build()
                    .parseClaimsJws(token);

            return Optional.ofNullable(jws.getBody().getSubject());
        } catch (DecodingException ignored) {
            log.warn("Failed to decode JWT secret key");
        } catch (ExpiredJwtException ignored) {
            log.warn("Expired JWT");
        } catch (UnsupportedJwtException | MalformedJwtException | SecurityException |
                 IllegalArgumentException ignored) {
            log.warn("Failed to parse JWT");
        }

        return Optional.empty();
    }

    public String createToken(String username, Long id) {
        SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(env.getProperty("jwt.secret-sign-key")));

        Map<String, Object> headers = new HashMap<>();
        headers.put("typ", "JWT");
        headers.put("alg", "HS256");

        Map<String, Object> claims = new HashMap<>();
        claims.put("id", id);

        return Jwts.builder()
                .setHeader(headers)
                .setClaims(claims)
                .setSubject(username) // sub
                // .setId("1") // jti
                .setExpiration(Date.from(LocalDateTime.now()
                                                 .plus(Duration.of(TOKEN_EXPIRES_IN, ChronoUnit.SECONDS))
                                                 .atZone(ZoneId.systemDefault()).toInstant())) // exp
                .signWith(key)
                .compact();
    }
}
