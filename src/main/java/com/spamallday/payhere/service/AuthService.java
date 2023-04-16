package com.spamallday.payhere.service;

import com.spamallday.payhere.dto.MemberDto;
import com.spamallday.payhere.dto.Token;
import com.spamallday.payhere.dto.TokenRequestDto;

public interface AuthService {
    Token login(MemberDto memberDto);
    void logout(TokenRequestDto tokenRequestDto);
    Token reissue(TokenRequestDto tokenRequestDto);
}
