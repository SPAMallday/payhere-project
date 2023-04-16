package com.spamallday.payhere.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

// Spring Security 사용을 위해 Bcrypt 인코더로 대체
// 사용하지 않음
public class Encrypt {
    private static final int SALT_SIZE = 32;

    /* Salt 생성 */
    public static String getSalt() {
        SecureRandom sr = new SecureRandom();
        byte[] salt = new byte[SALT_SIZE];

        // SALT_SIZE만큼 난수 바이트 주입
        sr.nextBytes(salt);

        // 바이트를 2자리 HEX 문자열로 변경
        StringBuffer sb = new StringBuffer();
        for(byte b : salt) {
            sb.append(String.format("%02x", b));
        }

        return sb.toString();
    }

    /* 암호화 */
    public static String getEncrypt(String password, String salt) {
        String result;

        try {
            // 문자열 암호화를 위한 객체 생성
            MessageDigest md = MessageDigest.getInstance("SHA-256");

            md.update((password + salt).getBytes());
            byte[] passwordSalted = md.digest();    // salt와 password를 합쳐서 SHA-256 알고리즘 적용

            StringBuffer sb = new StringBuffer();
            for(byte b : passwordSalted) {
                // 바이트를 2자리 HEX 문자열로 변경
                sb.append(String.format("%02x", b));
            }

            result = sb.toString();

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

        return result;
    }
}
