package kr.pincoin.be.auth.util;

import java.security.SecureRandom;

public class DjangoPasswordEncoder {
    private static SecureRandom random = new SecureRandom();

    public static final String ALGORITHM = "pbkdf2_sha256";
    public static final long ITERATIONS = 320000L;
    public static final String RANDOM_STRING_CHARS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    public String getSalt(int len) {
        // 영문대소문자 및 숫자 22자
        StringBuilder buff = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            int offset = random.nextInt(RANDOM_STRING_CHARS.length());
            buff.append(RANDOM_STRING_CHARS.substring(offset, offset + 1));
        }
        return buff.toString();
    }
}
