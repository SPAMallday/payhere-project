package com.spamallday.payhere.configuration;

import com.spamallday.payhere.util.Encrypt;
import org.springframework.security.crypto.password.PasswordEncoder;

// Spring Security 사용을 위해 Bcrypt 인코더로 대체
public class CustomPasswordEncoder implements PasswordEncoder {
    // 이미 암호화 과정을 만들어서 회원가입에 적용하고 있으므로 어쩔 수 없이 커스텀 인코더 사용

    @Override
    public String encode(CharSequence rawPassword) {
        // salt 생성
        String salt = Encrypt.getSalt();
        // salt와 Raw 패스워드 암호화
        return Encrypt.getEncrypt(rawPassword.toString(), salt);
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        // DB에 저장된 salt 가져와야하나?
        String salt= "23";
        // 입력받은 패스워드와 기존의 패스워드가 일치하는 지 확인
        return Encrypt.getEncrypt(rawPassword.toString(), salt).equals(encodedPassword);
    }
}
