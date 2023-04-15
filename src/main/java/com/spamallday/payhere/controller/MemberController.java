package com.spamallday.payhere.controller;

import com.spamallday.payhere.dto.JsonResponseDto;
import com.spamallday.payhere.dto.MemberDto;
import com.spamallday.payhere.exception.CustomErrorCode;
import com.spamallday.payhere.service.MemberServiceImpl;
import com.spamallday.payhere.util.JsonConverter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/member")
public class MemberController {

    private final MemberServiceImpl memberService;

    @Autowired
    MemberController(MemberServiceImpl memberService) {
        this.memberService = memberService;
    }

    @PostMapping("/signup")
    public ResponseEntity<JsonResponseDto> signUp(@Valid @RequestBody MemberDto memberDto) {
        // 전화번호 및 비밀번호 검증
        memberService.numberDigitValidation(memberDto);
        memberService.numberDuplicatedValidation(memberDto);

        // 회원 등록
        try {
            memberService.saveMember(memberDto);
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body(
                    JsonConverter.toJsonResponse(HttpStatus.INTERNAL_SERVER_ERROR, CustomErrorCode.SIGNUP_ERROR.getErrorMsg(), null));
        }

        // 201 Created로 redirect 수행하지 않고 200 OK로 처리
        return ResponseEntity.ok().body(JsonConverter.toOkNoDataJsonResponse());
    }
}
