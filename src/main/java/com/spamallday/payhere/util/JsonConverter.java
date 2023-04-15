package com.spamallday.payhere.util;

import com.spamallday.payhere.dto.JsonResponseDto;
import com.spamallday.payhere.dto.Meta;
import org.springframework.http.HttpStatus;

/* Json형태의 custom 응답을 만드는 Util */
public class JsonConverter {
    public static JsonResponseDto toOkNoDataJsonResponse() {
        return JsonResponseDto.builder()
                .meta(Meta.builder()
                        .code(HttpStatus.OK.value())
                        .msg("ok")
                        .build())
                .data(null)
                .build();
    }

    public static JsonResponseDto toBadJsonResponse(String msg) {
        return JsonResponseDto.builder()
                .meta(Meta.builder()
                        .code(HttpStatus.BAD_REQUEST.value())
                        .msg(msg)
                        .build())
                .data(null)
                .build();
    }

    public static JsonResponseDto toJsonResponse(HttpStatus httpStatus, String msg, Object data) {
        return JsonResponseDto.builder()
                .meta(Meta.builder()
                        .code(httpStatus.value())
                        .msg(msg).build())
                .data(data)
                .build();
    }
}
