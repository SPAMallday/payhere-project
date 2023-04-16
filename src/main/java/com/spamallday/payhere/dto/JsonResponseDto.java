package com.spamallday.payhere.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
public class JsonResponseDto {
    private Meta meta;
    private Object data;

    JsonResponseDto(Meta meta, Object data) {
        this.meta = meta;
        this.data = data;
    }
}
