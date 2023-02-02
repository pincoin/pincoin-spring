package kr.pincoin.be.member.jwt;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.Test;

import javax.crypto.SecretKey;
import java.util.Base64;

import static org.assertj.core.api.Assertions.assertThat;


class TokenProviderTest {
    @Test
    void HS256비밀키생성() {
        SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

        // base64 인코딩 문자열 (디비 저장용)
        String secretString = Base64.getEncoder().encodeToString(key.getEncoded());

        System.out.println("generated secret key: " + secretString);

        assertThat(secretString.length()).isEqualTo(44);
        assertThat(secretString.endsWith("=")).isTrue();

        assertThat(key.getEncoded()).isEqualTo(Base64.getDecoder().decode(secretString));
        assertThat(key.getAlgorithm()).isEqualTo("HmacSHA256");
        assertThat(key.getFormat()).isEqualTo("RAW");
    }
}