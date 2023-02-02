package kr.pincoin.be.auth.util;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;

public class DjangoPasswordEncoder {
    private static final SecureRandom random = new SecureRandom();

    public static final String ALGORITHM = "PBKDF2WithHmacSHA256";
    public static final int ITERATIONS = 390000;
    public static final String RANDOM_STRING_CHARS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    public String getSalt(int len) {
        StringBuilder buff = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            int offset = random.nextInt(RANDOM_STRING_CHARS.length());
            buff.append(RANDOM_STRING_CHARS.substring(offset, offset + 1));
        }
        return buff.toString();
    }

    public String getSalt() {
        return getSalt(22);
    }

    public String encrypt(String plain, String salt, int iterations) throws NoSuchAlgorithmException,
                                                                            InvalidKeySpecException {
        KeySpec keySpec = new PBEKeySpec(plain.toCharArray(),
                                         salt.getBytes(StandardCharsets.UTF_8),
                                         iterations,
                                         256); // 키 길이 256=44자, 512=88자

        SecretKey secret = SecretKeyFactory.getInstance(ALGORITHM).generateSecret(keySpec);

        return new String(Base64.getEncoder().encode(secret.getEncoded()));
    }

    public String encode(String plain, String salt, int iterations) throws NoSuchAlgorithmException,
                                                                           InvalidKeySpecException {
        String hash = encrypt(plain, salt, iterations);
        return String.format("%s$%d$%s$%s", "pbkdf2_sha256", iterations, salt, hash);
    }


    public boolean checkPassword(String plain, String encrypted) throws NoSuchAlgorithmException,
                                                                        InvalidKeySpecException {
        String[] parts = encrypted.split("\\$");

        if (parts.length != 4) {
            return false;
        }

        return encode(plain, parts[2], Integer.parseInt(parts[1])).equals(encrypted);
    }
}
