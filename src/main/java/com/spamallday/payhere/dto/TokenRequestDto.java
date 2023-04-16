package com.spamallday.payhere.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class TokenRequestDto {
    @NotEmpty(message = "Access Token을 확인해주세요.")
    private String accessToken;

    @NotEmpty(message = "Refresh Token을 확인해주세요.")
    private String refreshToken;
}
