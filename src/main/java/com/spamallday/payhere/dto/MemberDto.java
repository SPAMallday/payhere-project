package com.spamallday.payhere.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.spamallday.payhere.entity.Owner;
import com.spamallday.payhere.util.Encrypt;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@SuperBuilder
@ToString
@NoArgsConstructor
@JsonTypeInfo(use = JsonTypeInfo.Id.NONE,
        include = JsonTypeInfo.As.EXISTING_PROPERTY,
        // 만약 사장님 외 다른 멤버 타입을 추가하는 경우 입력값에 따라서 구분하여 직렬화 가능
        property = "memberType",    // 자식 클래스 구분 JSON 요소
        visible = false)             // 역직렬화 할 것인지
@JsonSubTypes({
        @JsonSubTypes.Type(name = "OWNER", value = OwnerDto.class), // 식별 property가 OWNER라면 OwnerDto로 변환
})
public class MemberDto {
    @NotBlank(message = "잘못된 휴대폰 번호입니다. 비어있거나 공백이 있습니다.")
    private String number;
    @NotBlank(message = "잘못된 비밀번호입니다. 비어있거나 공백이 있습니다.")
    private String password;
    @JsonIgnore
    private String salt;

    public void encrypt() {
        String salt = Encrypt.getSalt();

        this.password = Encrypt.getEncrypt(password, salt);
        this.salt = salt;
    }

    public Owner toOwnerEntity() {
        return Owner.builder()
                .number(getNumber())
                .password(getPassword())
                .salt(getSalt())
                .build();
    }
}
