package com.spamallday.payhere.configuration;

import com.spamallday.payhere.dto.JsonResponseDto;
import com.spamallday.payhere.util.JsonCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class ApiControllerAdvice {

    private final JsonCreator jsonCreator;

    @Autowired
    public ApiControllerAdvice(JsonCreator jsonCreator) {
        this.jsonCreator = jsonCreator;
    }

    // RequestBody가 입력됐을 때 @Valid에서 예외가 발생하는 경우 일괄 예외 처리
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<JsonResponseDto> handleValidationExceptions(MethodArgumentNotValidException ex) {
        StringBuilder msg = new StringBuilder();

        // 모든 에러를 불러와서 error msg에 추가
        ex.getBindingResult().getAllErrors()
                .forEach(c -> msg.append(c.getDefaultMessage()).append("\n"));

        // 반환 형식에 맞춰 에러 처리
        JsonResponseDto json = jsonCreator.toJsonResponse(HttpStatus.BAD_REQUEST, msg.toString(), null);

        return ResponseEntity.badRequest().body(json);
    }
}
