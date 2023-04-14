package com.spamallday.payhere.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@ToString
public abstract class MemberDto {
    @NotBlank
    private String number;
    @NotBlank
    private String password;
}
