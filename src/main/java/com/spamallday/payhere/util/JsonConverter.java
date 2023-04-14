package com.spamallday.payhere.util;

import com.spamallday.payhere.dto.JsonResponseDto;
import com.spamallday.payhere.dto.Meta;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

/* Json형태의 custom 응답을 만드는 Util */
@Component
public class JsonConverter {
    public  JsonResponseDto toJsonResponse(HttpStatus httpStatus, String msg, Object data) {
        return JsonResponseDto.builder()
                .meta(Meta.builder()
                        .code(httpStatus.value())
                        .msg(msg).build())
                .data(data)
                .build();
    }
}
