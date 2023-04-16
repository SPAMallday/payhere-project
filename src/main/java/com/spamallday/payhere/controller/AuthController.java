package com.spamallday.payhere.controller;

import com.spamallday.payhere.dto.JsonResponseDto;
import com.spamallday.payhere.dto.MemberDto;
import com.spamallday.payhere.dto.Token;
import com.spamallday.payhere.dto.TokenRequestDto;
import com.spamallday.payhere.service.AuthServiceImpl;
import com.spamallday.payhere.service.MemberServiceImpl;
import com.spamallday.payhere.util.JsonConverter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/auth")
public class AuthController {
    private final MemberServiceImpl memberService;
    private final AuthServiceImpl authService;

    @Autowired
    AuthController(MemberServiceImpl memberService, AuthServiceImpl authService) {
        this.memberService = memberService;
        this.authService = authService;
    }

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<JsonResponseDto> login(@Valid @RequestBody MemberDto memberDto) {
        // 전화번호 검증
        memberService.numberDigitValidation(memberDto);
        // 로그인
        Token token = authService.login(memberDto);

        // 201 Created로 redirect 수행하지 않고 200 OK로 처리
        return ResponseEntity.ok().body(JsonConverter.toJsonResponse(HttpStatus.OK,"로그인 성공",token));
    }

    // 로그아웃
    @PostMapping("/logout")
    public ResponseEntity<JsonResponseDto> logout(@RequestBody TokenRequestDto tokenRequestDto) {

        authService.logout(tokenRequestDto);

        return ResponseEntity.ok().body(JsonConverter.toOkNoDataJsonResponse());
    }

    // 토큰 재발행
    @PostMapping("/reissue")
    public ResponseEntity<JsonResponseDto> reissue(@RequestBody TokenRequestDto tokenRequestDto) {
        Token token = authService.reissue(tokenRequestDto);

        return ResponseEntity.ok().body(JsonConverter.toJsonResponse(HttpStatus.OK,"토큰 재발급 성공",token));
    }
}
