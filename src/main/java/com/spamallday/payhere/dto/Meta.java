package com.spamallday.payhere.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Meta {
    private int code;
    private String msg;
}
