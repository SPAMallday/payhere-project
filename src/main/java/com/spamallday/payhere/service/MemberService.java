package com.spamallday.payhere.service;

import com.spamallday.payhere.dto.MemberDto;

public interface MemberService {
    void numberDigitValidation(MemberDto memberDto);
    void numberDuplicatedValidation(MemberDto memberDto);
    void saveMember(MemberDto memberDto);

}
