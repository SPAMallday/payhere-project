package com.spamallday.payhere.service;

import com.spamallday.payhere.dto.MemberDto;

public interface MemberService {
    void numberValidation(MemberDto memberDto) throws Exception;
}
