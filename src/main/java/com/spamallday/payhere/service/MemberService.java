package com.spamallday.payhere.service;

import com.spamallday.payhere.dto.MemberDto;

public interface MemberService {
    void signUpValidation(MemberDto memberDto) throws Exception;
}
