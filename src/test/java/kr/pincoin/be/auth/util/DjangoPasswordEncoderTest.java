package kr.pincoin.be.auth.util;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;


class DjangoPasswordEncoderTest {
    private final DjangoPasswordEncoder encoder = new DjangoPasswordEncoder();

    @Test
    void salt생성() {
        String salt = encoder.getSalt(22);
        Assertions.assertThat(salt.length()).isEqualTo(22);
    }

    @Test
    void 해시생성() throws NoSuchAlgorithmException, InvalidKeySpecException {
        String encrypted = encoder.encode("pincoin1234", "XXkyxeekftVu2S5qC0yW9h", 390000);
        Assertions.assertThat(encrypted)
                .isEqualTo("pbkdf2_sha256$390000$XXkyxeekftVu2S5qC0yW9h$FOczZWDoQ+0sNYAP5cDvZgyoi5sYy84lpgY4ngVC5nU=");
    }

    @Test
    void 해시비교() throws NoSuchAlgorithmException, InvalidKeySpecException {
        boolean match = encoder.checkPassword("pincoin1234",
                                                    "pbkdf2_sha256$390000$XXkyxeekftVu2S5qC0yW9h$FOczZWDoQ+0sNYAP5cDvZgyoi5sYy84lpgY4ngVC5nU=");
        Assertions.assertThat(match).isTrue();
    }
}