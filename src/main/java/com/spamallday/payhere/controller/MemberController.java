package com.spamallday.payhere.controller;

import com.spamallday.payhere.dto.JsonResponseDto;
import com.spamallday.payhere.dto.MemberDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/member")
public class MemberController {

    @PostMapping("/signup")
    public ResponseEntity<JsonResponseDto> signUp(@Valid @RequestBody MemberDto memberDto) {



        return ResponseEntity.ok().build();
    }
}
