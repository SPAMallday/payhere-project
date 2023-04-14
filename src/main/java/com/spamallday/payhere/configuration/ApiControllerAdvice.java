package com.spamallday.payhere.configuration;

import com.spamallday.payhere.dto.JsonResponseDto;
import com.spamallday.payhere.util.JsonConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiControllerAdvice {

    private final JsonConverter jsonConverter;

    @Autowired
    public ApiControllerAdvice(JsonConverter jsonConverter) {
        this.jsonConverter = jsonConverter;
    }

    // RequestBody가 입력됐을 때 @Valid에서 예외가 발생하는 경우 일괄 예외 처리
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<JsonResponseDto> handleValidationExceptions(MethodArgumentNotValidException ex) {


        // 모든 에러를 불러와서 error msg에 추가
//        StringBuilder msg = new StringBuilder();
//        ex.getBindingResult().getAllErrors().forEach(c -> msg.append(c.getDefaultMessage()));

        // 첫 에러를 반환
        String msg = ex.getBindingResult().getAllErrors().get(0).getDefaultMessage();

        // 반환 형식에 맞춰 에러 처리
        JsonResponseDto json = jsonConverter.toJsonResponse(HttpStatus.BAD_REQUEST, msg, null);

        return ResponseEntity.badRequest().body(json);
    }
}
