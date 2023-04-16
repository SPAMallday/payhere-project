package com.spamallday.payhere.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Token {

    private String grantType;
    private String accessToken;
    private String refreshToken;
    private Long refreshTokenExpirationTime;

}