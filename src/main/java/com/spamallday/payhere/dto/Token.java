package com.spamallday.payhere.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Token {

    private String grantType;
    private String accessToken;
    private String refreshToken;

    @Builder
    private Token(String grantType, String accessToken, String refreshToken) {
        this.grantType = grantType;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}