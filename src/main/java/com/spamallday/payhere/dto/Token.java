package com.spamallday.payhere.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public class Token {

    private String grantType;
    private String accessToken;
    private String refreshToken;
}