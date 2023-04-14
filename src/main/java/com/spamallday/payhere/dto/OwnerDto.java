package com.spamallday.payhere.dto;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;


@SuperBuilder
@NoArgsConstructor
@ToString(callSuper = true)
public class OwnerDto extends MemberDto {
}
