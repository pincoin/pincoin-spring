package kr.pincoin.api.auth.password;

import org.springframework.security.crypto.password.PasswordEncoder;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;

public class DjangoPasswordEncoder implements PasswordEncoder {
    private static final SecureRandom random = new SecureRandom();

    public static final String ALGORITHM = "PBKDF2WithHmacSHA256";
    public static final int ITERATIONS = 390000;
    public static final String RANDOM_STRING_CHARS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    public String
    getSalt(int len) {
        StringBuilder buff = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            int offset = random.nextInt(RANDOM_STRING_CHARS.length());
            buff.append(RANDOM_STRING_CHARS.charAt(offset));
        }
        return buff.toString();
    }

    public String
    getSalt() {
        return getSalt(22);
    }

    public String
    encrypt(String plain, String salt, int iterations) throws NoSuchAlgorithmException,
                                                              InvalidKeySpecException {
        KeySpec keySpec = new PBEKeySpec(plain.toCharArray(),
                                         salt.getBytes(StandardCharsets.UTF_8),
                                         iterations,
                                         256); // 키 길이 256=44자, 512=88자

        SecretKey secret = SecretKeyFactory.getInstance(ALGORITHM).generateSecret(keySpec);

        return new String(Base64.getEncoder().encode(secret.getEncoded()));
    }

    public String
    encode(String plain, String salt, int iterations) throws NoSuchAlgorithmException,
                                                             InvalidKeySpecException {
        String hash = encrypt(plain, salt, iterations);
        return String.format("%s$%d$%s$%s", "pbkdf2_sha256", iterations, salt, hash);
    }

    @Override
    public String
    encode(CharSequence rawPassword) {
        try {
            return encode(rawPassword.toString(), getSalt(), ITERATIONS);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException ignored) {
            return null;
        }
    }

    @Override
    public boolean
    matches(CharSequence rawPassword, String encodedPassword) {
        String[] parts = encodedPassword.split("\\$");

        if (parts.length != 4) {
            return false;
        }

        try {
            return encode(rawPassword.toString(), parts[2], Integer.parseInt(parts[1])).equals(encodedPassword);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException ignored) {
            return false;
        }
    }
}
