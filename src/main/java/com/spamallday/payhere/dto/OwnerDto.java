package com.spamallday.payhere.dto;

import com.spamallday.payhere.entity.Owner;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;


@SuperBuilder
@NoArgsConstructor
@ToString(callSuper = true)
public class OwnerDto extends MemberDto {
    public Owner toEntity() {
        return Owner.builder()
                .number(getNumber())
                .password(getPassword())
//                .salt(getSalt())
                .build();
    }
}
