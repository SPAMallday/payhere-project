package com.spamallday.payhere.service;

import com.spamallday.payhere.auth.JwtTokenProvider;
import com.spamallday.payhere.dto.MemberDto;
import com.spamallday.payhere.dto.Token;
import com.spamallday.payhere.dto.TokenRequestDto;
import com.spamallday.payhere.entity.Member;
import com.spamallday.payhere.exception.CustomErrorCode;
import com.spamallday.payhere.exception.CustomException;
import com.spamallday.payhere.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.transaction.Transactional;
import java.util.concurrent.TimeUnit;

@Service
public class AuthServiceImpl implements AuthService {
    private final MemberRepository memberRepository;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;
    private final StringRedisTemplate stringRedisTemplate;

    @Autowired
    AuthServiceImpl(MemberRepository memberRepository, AuthenticationManagerBuilder authenticationManagerBuilder,
                    JwtTokenProvider jwtTokenProvider, StringRedisTemplate stringRedisTemplate) {
        this.memberRepository = memberRepository;
        this.authenticationManagerBuilder = authenticationManagerBuilder;
        this.jwtTokenProvider = jwtTokenProvider;
        this.stringRedisTemplate = stringRedisTemplate;
    }

    @Transactional
    @Override
    public Token login(MemberDto memberDto) {
        // ID/PW로 Authentication 객체 생성, authenticated = false
        UsernamePasswordAuthenticationToken authenticationToken = memberDto.toAuth();

        // 실제 검증 (사용자 비밀번호 체크)
        // authenticate 매서드가 실행될 때 CustomUserDetailsService 에서 만든 loadUserByUsername 메서드가 실행
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        // 회원 DB의 PK값을 가져옴
        Member member = memberRepository.findUserIdByNumber(authenticationToken.getPrincipal().toString());
        Integer userId = member.getId();

        // 인증 정보를 기반으로 JWT 토큰 생성
        Token token = jwtTokenProvider.generateToken(authentication, userId);

        // RefreshToken Redis 저장 (expirationTime 설정을 통해 자동 삭제)
        stringRedisTemplate.opsForValue()
                .set("RT:" + authentication.getName(), token.getRefreshToken(),
                        token.getRefreshTokenExpirationTime(), TimeUnit.MILLISECONDS);

        return token;
    }

    @Transactional
    @Override
    public void logout(TokenRequestDto tokenRequestDto) {
            // 로그아웃 할 토큰이 유효한 지 검증
            if (!jwtTokenProvider.validateToken(tokenRequestDto.getAccessToken())){
                throw new CustomException(CustomErrorCode.TOKEN_VALID_ERROR);
            }

            // Access Token에서 휴대폰 번호 가져옴
            Authentication authentication = jwtTokenProvider.getAuthentication(tokenRequestDto.getAccessToken());

            // Redis에서 해당 휴대폰 번호로 저장된 Refresh Token 이 있다면 삭제
            if (stringRedisTemplate.opsForValue().get("RT:"+authentication.getName())!=null){
                // Refresh Token을 삭제
                stringRedisTemplate.delete("RT:"+authentication.getName());
            }

            // 해당 Access Token을 Redis에 Expire를 지정하여 logout 값과 함께 저장 -> 로그인 시 조회하여 해당 액세스 토큰이 있으면 로그인 방지
            Long expiration = jwtTokenProvider.getExpiration(tokenRequestDto.getAccessToken());
            stringRedisTemplate.opsForValue().set(tokenRequestDto.getAccessToken(),"logout",expiration,TimeUnit.MILLISECONDS);
    }

    @Transactional
    @Override
    public Token reissue(TokenRequestDto tokenRequestDto) {
        // Refresh Token 검증
        if (!jwtTokenProvider.validateToken(tokenRequestDto.getRefreshToken())) {
            throw new CustomException(CustomErrorCode.TOKEN_REFRESH_VALID_ERROR);
        }

        // Access Token 에서 휴대폰 번호 가져오기
        Authentication authentication = jwtTokenProvider.getAuthentication(tokenRequestDto.getAccessToken());

        // Redis 에서 휴대폰 번호 기반으로 저장된 Refresh Token 값 조회
        String refreshToken = stringRedisTemplate.opsForValue().get("RT:" + authentication.getName());

        // 로그아웃되어 Redis 에 RefreshToken 이 존재하지 않는 경우
        if(ObjectUtils.isEmpty(refreshToken)) {
            throw new CustomException(CustomErrorCode.BAD_REQUEST_ERROR);
        }
        // Refresh Token이 일치하지 않는 경우
        if(!refreshToken.equals(tokenRequestDto.getRefreshToken())) {
            throw new CustomException(CustomErrorCode.TOKEN_REFRESH_NOT_EQUAL_ERROR);
        }

        // 새로운 토큰 생성
        Token token = jwtTokenProvider.generateToken(
                authentication, (Integer) jwtTokenProvider.parseClaims(tokenRequestDto.getAccessToken()).get("id"));

        // RefreshToken Redis 업데이트
        stringRedisTemplate.opsForValue()
                .set("RT:" + authentication.getName(), token.getRefreshToken(), token.getRefreshTokenExpirationTime(), TimeUnit.MILLISECONDS);

        return token;
    }
}
